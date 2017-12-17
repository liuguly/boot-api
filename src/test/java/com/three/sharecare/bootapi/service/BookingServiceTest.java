package com.three.sharecare.bootapi.service;

import com.three.sharecare.bootapi.domain.Account;
import com.three.sharecare.bootapi.domain.Booking;
import com.three.sharecare.bootapi.domain.Payment;
import com.three.sharecare.bootapi.dto.BookingRequest;
import com.three.sharecare.bootapi.dto.PaymentRequest;
import com.three.sharecare.bootapi.repository.AccountDao;
import com.three.sharecare.bootapi.repository.BookingDao;
import com.three.sharecare.bootapi.repository.PaymentDao;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Test
    public void testBooking(){
        BookingRequest request = new BookingRequest();
        request.setCareId(2L);
        request.setCareType(1);
//        bookingService.bookingCare("BDC123456",request);
    }

    @Autowired
    private BookingDao bookingDao ;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PaymentDao paymentDao;

    @Test
    @Transactional
    @Rollback(false)
    public void bookingPayment(){
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(0.01);
            paymentRequest.setCareType(0);
            paymentRequest.setChildNums(1);
            paymentRequest.setDays(1);
            paymentRequest.setPaymentEmailOrCardNumber("testeste");
            paymentRequest.setPaymentMethodNonce("methodtest");
            paymentRequest.setPaymentType(13);
            paymentRequest.setShareId(48L);
            paymentRequest.setUnitPrice("0.01");
            Account account = accountDao.findOne(14L);
            Booking booking = bookingDao.findOne(44L);
            Payment payment = new Payment();
            BeanUtils.copyProperties(paymentRequest,payment);
            payment.setAccount(account);
            payment.setAmount(paymentRequest.getAmount()+"");
            String currentDate = DateFormatUtils.format(new Date(),"yyyyMMddHHmmSS");
            payment.setReceiptCode(currentDate.concat(14+""));
            payment.setCreateTime(new Date());
            payment.setUpdateTime(new Date());
            payment = paymentDao.save(payment);
            bookingDao.updatePaymentId(payment.getId(),booking.getId());
            System.out.println(payment.getId());
        }
    }

