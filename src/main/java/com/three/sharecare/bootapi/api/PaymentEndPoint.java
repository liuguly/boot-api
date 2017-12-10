package com.three.sharecare.bootapi.api;

import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.domain.CreditCard;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.CreditCardService;
import com.three.sharecare.bootapi.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "支付")
@RestController
@RequestMapping(value = "/v1/payment")
public class PaymentEndPoint {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CreditCardService creditCardService;

    @ApiOperation(value = "获取支付的token",notes = "获取支付的token")
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseResult<String> getPaymentToken(@RequestHeader(value = "token")String token){
        AccountDto accountDto = accountService.checkToken(token);
        String data = paymentService.getToken(accountDto);
        ResponseResult<String> result = new ResponseResult<>(true,"get payment token success!",data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "支付费用",notes = "支付费用")
    @RequestMapping(value = "/transaction",method = RequestMethod.POST)
    public ResponseResult<String> payment(@RequestHeader(value = "token") String token,
                                          @RequestBody @Valid PaymentRequest request){
        AccountDto accountDto = accountService.checkToken(token);
        String data = paymentService.paymentByNonce(accountDto,request);
        ResponseResult<String> result = new ResponseResult<>(true,"payment success!",data, HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "根据事物id查询支付详情",notes = "根据事物id查询支付详情")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public ResponseResult<PaymentDetail> findPaymentDetail(@RequestHeader(value = "token") String token,
                                                           @PathVariable(value = "transactionId") String transactionId){
        AccountDto accountDto = accountService.checkToken(token);
        PaymentDetail data = paymentService.findPaymentDetailBy(accountDto,transactionId);
        ResponseResult<PaymentDetail> result = new ResponseResult<>(true,"find payment detail success!",data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "绑定信用卡",notes = "绑定信用卡")
    @RequestMapping(value = "/addCreditCard",method = RequestMethod.POST)
    public ResponseResult<Long> addCreditCard(@RequestHeader(value = "token") String token,
                                              @RequestBody CreditCardDto creditCardDto) {
        AccountDto accountDto = accountService.checkToken(token);
        CreditCard creditCard = creditCardService.saveCreditCard(accountDto,creditCardDto);
        ResponseResult<Long> result = new ResponseResult<>(true,"add credit card success!", creditCard.getId(), HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "解绑信用卡",notes = "解绑信用卡")
    @RequestMapping(value = "/delete/creditCard/{creditCardId}",method = RequestMethod.GET)
    public ResponseResult<Integer> removeCreditCard(@RequestHeader(value = "token") String token,
                                                 @PathVariable(value = "creditCardId") Long creditCardId){
        AccountDto accountDto = accountService.checkToken(token);
        Integer data = creditCardService.deleteCreditCard(accountDto,creditCardId);
        ResponseResult<Integer> result = new ResponseResult<>(true,"remove credit card success!", data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找自己的信用卡",notes = "查找自己的信用卡信息")
    @RequestMapping(value = "/creditCard/list",method = RequestMethod.GET)
    public ResponseResult<List<CreditCardDto>> findCreditCardInfo(@RequestHeader(value = "token") String token){
        AccountDto accountDto = accountService.checkToken(token);
        List<CreditCardDto> data = creditCardService.findCreditCardInfo(accountDto);
        ResponseResult<List<CreditCardDto>> result = new ResponseResult<>(true,"find credit card info success!",data,HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找支付详情",notes = "查找支付详情")
    @RequestMapping(value = "/detail/{careType}/{typeId}",method = RequestMethod.GET)
    public ResponseResult<PaymentDto> findPaymentDetail(@RequestHeader(value = "token") String token,
                                                        @PathVariable(value = "careType") Integer careType,
                                                        @PathVariable(value = "typeId") Long typeId){
        AccountDto accountDto = accountService.checkToken(token);
        PaymentDto data = paymentService.findPaymentDetail(accountDto,typeId,careType);
        ResponseResult<PaymentDto> result = new ResponseResult<>(true,"find payment info success!",data,HttpStatus.OK.value());
        return result;
    }

}
