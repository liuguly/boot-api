package com.three.sharecare.bootapi.api;


import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.FindAllShareDto;
import com.three.sharecare.bootapi.dto.SearchShareByConditionDto;
import com.three.sharecare.bootapi.dto.ShareCareDto;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.ShareCareService;
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

@Api(value = "分享照顾")
@RestController
@RequestMapping(value = "/v1/sharecare")
public class ShareCareEndPoint {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ShareCareService shareCareService;

    @ApiOperation(value = "分享发布", notes = "分享发布照顾小孩的信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult<Long> saveSharecare(HttpServletRequest request,
                                              @RequestPart(value = "file") MultipartFile[] multipartFiles,
                                              @Valid ShareCareDto shareCareDto) {
        Long id = shareCareService.saveShareCare(request, multipartFiles, shareCareDto);
        ResponseResult<Long> result = new ResponseResult<>(true, "share success!", id, HttpStatus.OK.value());
        return result;
    }

    /**
     * 分页对象
     *
     * @param pageable 分页对象
     * @return 分页结果
     */
    @ApiOperation(value = "查找发布的sharecare信息列表", notes = "查找发布的sharecare信息列表")
    @RequestMapping(value = "/list/pageable", method = RequestMethod.POST)
    public ResponseResult<Page<ShareCareDto>> findShareList(@RequestHeader(value = "token") String token,
                                                            @RequestBody @Valid SearchShareByConditionDto searchShareByConditionDto,
                                                            @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<ShareCareDto> shareCareList = shareCareService.findShareCareList(accountDto,searchShareByConditionDto, pageable);
        ResponseResult<Page<ShareCareDto>> result = new ResponseResult<>(true, "query share care list success!", shareCareList, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找一个sharecare的详情", notes = "查找一个sharecare的详情")
    @RequestMapping(value = "/one/{shareCareId}", method = RequestMethod.GET)
    public ResponseResult<ShareCareDto> findOneSharecare(@RequestHeader(value = "token") String token,
                                                         @PathVariable(value = "shareCareId") Long shareCareId){
        AccountDto accountDto = accountService.checkToken(token);
        ShareCareDto data = shareCareService.findOneShareCare(accountDto,shareCareId);
        ResponseResult<ShareCareDto> result = new ResponseResult<>(true, "query share care success!", data, HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "查找所有内容", notes = "查找所有内容")
    @RequestMapping(value = "/share/all/pageable", method = RequestMethod.POST)
    public ResponseResult<FindAllShareDto> findAllShare(@RequestHeader(value = "token") String token,
                                                        @RequestBody @Valid SearchShareByConditionDto searchShareByConditionDto,
                                                        @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        FindAllShareDto data = shareCareService.findAllShare(accountDto,searchShareByConditionDto, pageable);
        ResponseResult<FindAllShareDto> result = new ResponseResult<>(true, "query all share success!", data, HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "查找自己发布的sharecare", notes = "查找自己发布的sharecare")
    @RequestMapping(value = "/myself/list/pageable", method = RequestMethod.GET)
    public ResponseResult<Page<ShareCareDto>> findShareCareListByMyself(@RequestHeader(value = "token") String token,
                                                                        @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<ShareCareDto> data = shareCareService.findSharecareListByMyself(accountDto, pageable);
        ResponseResult<Page<ShareCareDto>> result = new ResponseResult<>(true, "query share care success!", data, HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "根据nearby、分享类型、经纬度查找", notes = "根据nearby、分享类型、经纬度查找")
    @RequestMapping(value = "/search/all/", method = RequestMethod.POST)
    public ResponseResult<FindAllShareDto> searchAllShare(@RequestHeader(value = "token") String token,
                                                          @Valid SearchShareByConditionDto searchShareByConditionDto,
                                                          @PageableDefault(value = 15, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        FindAllShareDto data = shareCareService.findAllShare(accountDto,searchShareByConditionDto, pageable);
        ResponseResult<FindAllShareDto> result = new ResponseResult<>(true, "query all share success!", data, HttpStatus.OK.value());
        return result;
    }

}
