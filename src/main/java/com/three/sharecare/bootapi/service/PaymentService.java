package com.three.sharecare.bootapi.service;

import com.braintreegateway.*;
import com.fasterxml.jackson.databind.JavaType;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.repository.AccountDao;
import com.three.sharecare.bootapi.repository.PaymentDao;
import com.three.sharecare.bootapi.service.exception.ErrorCode;
import com.three.sharecare.bootapi.service.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentService extends BaseService{


    private JsonMapper jsonMapper = new JsonMapper();

    @Value(value = "${payment.bt.environment}")
    private String bt_environment;
    @Value(value = "${payment.bt.merchant.id}")
    private String bt_merchant_id;
    @Value(value = "${payment.bt.public.key}")
    private String bt_public_key;
    @Value(value = "${payment.bt.private.key}")
    private String bt_private_key;

    private BraintreeGateway braintreeGateway ;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private AccountDao accountDao;

    @PostConstruct
    public void initGateway(){
        braintreeGateway = new BraintreeGateway(bt_environment,bt_merchant_id,bt_public_key,bt_private_key);
    }

    /**
     * 获取token
     * @return 记录
     */
    @Cacheable(cacheNames = "paymentCache", key = "#accountDto.id")
    public String getToken(AccountDto accountDto){
//        ClientTokenRequest clientTokenRequest = new ClientTokenRequest()
//                .customerId(accountDto.getEmail())
//                .merchantAccountId(accountDto.getId()+"");
        return this.braintreeGateway.clientToken().generate();
    }

    public PaymentDto findPaymentDetail(AccountDto accountDto, Long shareId, Integer careType){
        Payment payment = paymentDao.findByShareIdAndCareTypeAndAccount_Id(shareId,careType,accountDto.getId());
        PaymentDto paymentDto = new PaymentDto();
        Booking booking = bookingDao.findBookingByCareTypeAndTypeIdAndAccount_Id(careType,shareId,accountDto.getId());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setChildNums(payment.getChildNums());
        paymentDto.setDays(payment.getDays());
        paymentDto.setUnitPrice(payment.getUnitPrice());
        Account account = payment.getAccount();
        if(account!=null){
            UserInfo userInfo = account.getUserInfo();
            if(userInfo!=null){
                String childInfo = userInfo.getChildren();
                JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
                List<UserInfoDto.ChildrenInfo> result = jsonMapper.fromJson(childInfo,javaType);
                paymentDto.setChildrenInfos(result);
            }
        }
        paymentDto.setAppointTime(booking.getStartDate());
        paymentDto.setMasterCard(payment.getPaymentEmailOrCardNumber());
        paymentDto.setPaymentReceivedTime(payment.getCreateTime());
        if(careType==0){
            ShareCare shareCare = shareCareDao.findOne(shareId);
            paymentDto.setAddress(shareCare.getAddress());
        }else if(careType==1){
            if(account!=null){
                UserInfo userInfo = account.getUserInfo();
                if(userInfo!=null){
                    paymentDto.setAddress(userInfo.getAddress());
                }
            }
        }else if(careType==2){
            Event event = eventDao.findOne(shareId);
            paymentDto.setAddress(event.getAddress());
        }
        return paymentDto;
    }


    /**
     * 支付
     * @param request 支付请求
     * @return 返回transaction的id
     */
    public String paymentByNonce(AccountDto accountDto,PaymentRequest request){
        double totalAmount = request.getAmount();
        String nonce = request.getPaymentMethodNonce();
        BigDecimal decimalAmount = new BigDecimal(totalAmount).setScale(2, BigDecimal.ROUND_DOWN);

        TransactionRequest paytRequest = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = braintreeGateway.transaction().sale(paytRequest);
        if(result.isSuccess()){
            Transaction transaction = result.getTarget();
            savePaymentInfo(accountDto,request,transaction);
            return transaction.getId();
        }else if(result.getTransaction() !=null){
            Transaction transaction = result.getTransaction();
            savePaymentInfo(accountDto,request,transaction);
            return transaction.getId();
        }else {
            throw new ServiceException(jsonMapper.toJson(result.getErrors().getAllValidationErrors()), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Payment savePaymentInfo(AccountDto accountDto,PaymentRequest paymentRequest,Transaction transaction){
        Long accountId = accountDto.getId();
        Account account = accountDao.findOne(accountId);
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentRequest,payment);
        payment.setAccount(account);
        payment.setCreateTime(new Date());
        payment.setUpdateTime(new Date());
        payment = paymentDao.save(payment);
        return payment;
    }

    /**
     * 获取支付详情
     * @param transactionId 事物id
     * @return 支付详情
     */
    public PaymentDetail findPaymentDetailBy(AccountDto accountDto,String transactionId){
        PaymentDetail paymentDetail = new PaymentDetail();
        try {
            Transaction transaction = braintreeGateway.transaction().find(transactionId);
            com.braintreegateway.CreditCard creditCard = transaction.getCreditCard();
            Customer customer = transaction.getCustomer();
            paymentDetail.setCreditCard(creditCard);
            paymentDetail.setTransaction(transaction);
            paymentDetail.setCustomer(customer);
            return paymentDetail;
        }catch (Exception e){
            throw new ServiceException(e.getMessage(),ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
