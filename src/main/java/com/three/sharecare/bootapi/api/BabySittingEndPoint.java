package com.three.sharecare.bootapi.api;


import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.BabySittingDto;
import com.three.sharecare.bootapi.dto.SearchShareByConditionDto;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.BabySittingService;
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

@Api(value = "专职看小孩")
@RestController
@RequestMapping(value = "/v1/babysitting")
public class BabySittingEndPoint {

    @Autowired
    private BabySittingService babySittingService;

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "专职保姆", notes = "发布专职保姆信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult<Long> saveBabySitting(HttpServletRequest request,
                                                @RequestPart(value = "file") MultipartFile[] multipartFiles,
                                                @Valid BabySittingDto babySittingDto) {
        String token = request.getHeader("token");
        accountService.checkToken(token);
        Long id = babySittingService.saveBabySitting(request, multipartFiles, babySittingDto);
        ResponseResult<Long> result = new ResponseResult<>(true, "share success!", id, HttpStatus.OK.value());
        return result;
    }

    /**
     * 分页对象
     *
     * @param pageable 分页对象
     * @return 分页结果
     */
    @ApiOperation(value = "专职保姆列表", notes = "专职看小孩信息列表")
    @RequestMapping(value = "/list/pageable", method = RequestMethod.POST)
    public ResponseResult<Page<BabySittingDto>> findBabySittingList(
            @RequestHeader(value = "token") String token,
            @RequestBody @Valid SearchShareByConditionDto searchShareByConditionDto,
            @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<BabySittingDto> babySittings = babySittingService.findBabySitting(accountDto, searchShareByConditionDto, pageable);
        ResponseResult<Page<BabySittingDto>> result = new ResponseResult<>(true, "query baby sitting list success!", babySittings, HttpStatus.OK.value());
        return result;
    }

    /**
     * 查找自己发布的
     *
     * @param pageable 分页对象
     * @return 分页结果
     */
    @ApiOperation(value = "获取自己发布的保姆信息", notes = "获取自己发布的保姆信息")
    @RequestMapping(value = "/myself/list/pageable", method = RequestMethod.GET)
    public ResponseResult<Page<BabySittingDto>> findBabySittingListByMySelf(
            @RequestHeader(value = "token") String token,
            @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<BabySittingDto> babySittings = babySittingService.findBabySittingByMyself(accountDto, pageable);
        ResponseResult<Page<BabySittingDto>> result = new ResponseResult<>(true, "query baby sitting list success!", babySittings, HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "详情", notes = "查看一条BabySitting详情")
    @RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
    public ResponseResult<BabySittingDto> findBabySittingDetail(
            @RequestHeader(value = "token") String token,
            @PathVariable(value = "id") Long id) {
        AccountDto accountDto = accountService.checkToken(token);
        BabySittingDto babySitting = babySittingService.findOneBabySitting(accountDto, id);
        ResponseResult<BabySittingDto> result = new ResponseResult<>(true, "query baby sitting success!", babySitting, HttpStatus.OK.value());
        return result;
    }

}
