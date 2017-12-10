package com.three.sharecare.bootapi.api;

import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.domain.Booking;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "预定")
@RestController
@RequestMapping(value = "/v1/booking")
public class BookingEndPoint {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookingService bookingService;

    /**
     * 预定
     *
     * @param token   令牌
     * @param request 请求
     * @return 预定
     */
    @ApiOperation(value = "对分享进行预约", notes = "预约分享信息")
    @RequestMapping(value = "/appoint", method = RequestMethod.POST)
    public ResponseResult<Long> bookingShare(@RequestHeader(value = "token") String token, @RequestBody @Valid BookingRequest request) {
        AccountDto accountDto = accountService.checkToken(token);
        Booking booking = bookingService.bookingCare(accountDto, request);
        ResponseResult<Long> result = new ResponseResult<>(true, "booking success!", booking.getId(), HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "获取booking列表信息", notes = "获取booking列表信息")
    @RequestMapping(value = "/appoint/list/pageable", method = RequestMethod.GET)
    public ResponseResult<Page<BookingDto>> findBookingList(@RequestHeader(value = "token") String token,
                                                            @RequestParam(name = "shareType", required = false) Integer shareType,
                                                            @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<BookingDto> data = bookingService.getBookingList(accountDto, shareType, pageable);
        ResponseResult<Page<BookingDto>> result = new ResponseResult<>(true, "find me booking list success!", data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "获取别人booking me的列表", notes = "获取别人booking me的列表")
    @RequestMapping(value = "/others/appoint/list/pageable", method = RequestMethod.GET)
    public ResponseResult<Page<BookingDto>> findByOthersBookingMe(@RequestHeader(value = "token") String token,
                                                                  @RequestParam(name = "shareType") Integer shareType,
                                                                  @PageableDefault(sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<BookingDto> data = bookingService.getBookingListByOthersBookingMe(accountDto,shareType,pageable);
        ResponseResult<Page<BookingDto>> result = new ResponseResult<>(true, "find others booking me list success!", data, HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "获取已预定的小孩信息", notes = "获取已预定的小孩信息")
    @RequestMapping(value = "/appointed/childInfo/{shareType}/{shareId}", method = RequestMethod.GET)
    public ResponseResult<List<UserInfoDto.ChildrenInfo>> findAppointedChildInfo(@RequestHeader(value = "token") String token,
                                                                                   @PathVariable(name = "shareType") Integer shareType,
                                                                                   @PathVariable(name = "shareId") Long shareId) {
        AccountDto accountDto = accountService.checkToken(token);
        List<UserInfoDto.ChildrenInfo> data = bookingService.getAppointedBookingList(accountDto, shareType, shareId);
        ResponseResult<List<UserInfoDto.ChildrenInfo>> result = new ResponseResult<>(true,
                "find appointed child info success!", data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "获取past的booking",notes = "获取past的booking")
    @RequestMapping(value = "/past/list",method = RequestMethod.GET)
    public ResponseResult<Page<BookingDto>> findPastBookingList(@RequestHeader(value = "token") String token,
                                                                @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<BookingDto> data = bookingService.findPastBooking(accountDto,pageable);
        ResponseResult<Page<BookingDto>> result = new ResponseResult<>(true,"find past booking list success!", data,HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "获取coming的booking",notes = "获取coming的booking")
    @RequestMapping(value = "/upComing/list",method = RequestMethod.GET)
    public ResponseResult<Page<BookingDto>> findComingBookingList(@RequestHeader(value = "token") String token,
                                                                  @PageableDefault(sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<BookingDto> data = bookingService.findUpCommingBooking(accountDto,pageable);
        ResponseResult<Page<BookingDto>> result = new ResponseResult<>(true,"find past booking list success!", data,HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "对订单执行confirm操作",notes = "对订单执行confirm操作")
    @RequestMapping(value = "/confirm/{bookingId}",method = RequestMethod.GET)
    public ResponseResult<String> acceptBooking(@RequestHeader(value = "token") String token,
                                                @PathVariable(value = "bookingId") Long bookingId){
        AccountDto accountDto = accountService.checkToken(token);
        bookingService.acceptBooking(accountDto,bookingId);
        ResponseResult<String> result = new ResponseResult<>(true,"booking confirm success!", "",HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "对订单执行reject操作",notes = "对订单执行reject操作")
    @RequestMapping(value = "/reject",method = RequestMethod.POST)
    public ResponseResult<String> rejectBooking(@RequestHeader(value = "token") String token,
                                                @RequestBody @Valid RejectRequestDto rejectRequestDto){
        AccountDto accountDto = accountService.checkToken(token);
        bookingService.rejectBooking(accountDto,rejectRequestDto);
        ResponseResult<String> result = new ResponseResult<>(true,"booking reject success!", "",HttpStatus.OK.value());
        return result;
    }

}
