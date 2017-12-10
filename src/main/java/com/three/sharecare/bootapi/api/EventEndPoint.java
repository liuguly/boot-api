package com.three.sharecare.bootapi.api;

import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.EventDto;
import com.three.sharecare.bootapi.dto.SearchShareByConditionDto;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(value = "发布分享事件")
@RestController
@RequestMapping(value = "/v1/event")
public class EventEndPoint {

    @Autowired
    private EventService eventService;

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "发布event", notes = "发布一个事件")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult<Long> saveEvent(HttpServletRequest request,
                                          @RequestPart(value = "file") MultipartFile[] multipartFiles,
                                          @Valid EventDto eventDto){
        String token = request.getHeader("token");
        accountService.checkToken(token);
        Long id = eventService.saveEvent(request,multipartFiles,eventDto);
        ResponseResult<Long> result = new ResponseResult<>(true, "share success!", id, HttpStatus.OK.value());
        return result;
    }

    /**
     * 分页对象
     * @param pageable 分页对象
     * @return 分页结果
     */
    @ApiOperation(value = "查找发布的Event列表", notes = "查找发布的Event列表")
    @RequestMapping(value = "/list/pageable", method = RequestMethod.POST)
    public ResponseResult<Page<EventDto>> findEventList(
            @RequestHeader(value = "token")String token,
            @RequestBody @Valid SearchShareByConditionDto searchShareByConditionDto,
            @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC)Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<EventDto> eventList = eventService.findEventList(accountDto,searchShareByConditionDto,pageable);
        ResponseResult<Page<EventDto>> result = new ResponseResult<>(true, "query event list success!", eventList, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找一条event的详情", notes = "查找一条event的详情")
    @RequestMapping(value = "/one/{eventId}", method = RequestMethod.GET)
    public ResponseResult<EventDto> findOneEvent(@RequestHeader(value = "token")String token,
                                                 @PathVariable(value = "eventId") Long eventId){
        AccountDto accountDto = accountService.checkToken(token);
        EventDto data = eventService.findOneEvent(accountDto,eventId);
        ResponseResult<EventDto> result = new ResponseResult<>(true, "query event success!", data, HttpStatus.OK.value());
        return result;
    }


    /**
     * 分页对象
     * @param pageable 分页对象
     * @return 分页结果
     */
    @ApiOperation(value = "获取自己发布的Event列表", notes = "获取自己发布的Event列表")
    @RequestMapping(value = "/myself/list/pageable", method = RequestMethod.GET)
    public ResponseResult<Page<EventDto>> findEventListByMyself(
            @RequestHeader(value = "token")String token,
            @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC)Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<EventDto> eventList = eventService.findEventListByMyself(accountDto,pageable);
        ResponseResult<Page<EventDto>> result = new ResponseResult<>(true, "query event list success!", eventList, HttpStatus.OK.value());
        return result;
    }
}
