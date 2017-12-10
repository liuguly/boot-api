package com.three.sharecare.bootapi.service;

import com.three.sharecare.bootapi.dto.BookingRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

}
