package com.three.sharecare.bootapi.api;

import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.FavoriteService;
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

@Api(value = "喜欢")
@RestController
@RequestMapping(value = "/v1/favorite")
public class FavoriteEndPoint {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FavoriteService favoriteService;


    @ApiOperation(value = "点赞或者取消点赞", notes = "点赞或者取消点赞")
    @RequestMapping(value = "/addOrRemove", method = RequestMethod.POST)
    public ResponseResult<FavoriteDto> addOrRemoveFavorite(@RequestHeader(value = "token") String token,
                                                           @RequestBody @Valid FavoriteRequest request) {
        AccountDto accountDto = accountService.checkToken(token);
        FavoriteDto data = favoriteService.saveFavorite(accountDto, request);
        ResponseResult<FavoriteDto> result = new ResponseResult<>(true, "success!", data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找喜欢的分享列表")
    @RequestMapping(value = "/sharecare/list", method = RequestMethod.GET)
    public ResponseResult<Page<ShareCareDto>>
    findFavoriteShareCare(@RequestHeader(value = "token") String token,
                          @PageableDefault(value = 15, sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<ShareCareDto> data = favoriteService.findFavoriteSharecare(accountDto, 0, pageable);
        ResponseResult<Page<ShareCareDto>> result = new ResponseResult<>(true, "query favorite share care success!", data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找喜欢的BabySitting列表")
    @RequestMapping(value = "/babysitting/list", method = RequestMethod.GET)
    public ResponseResult<Page<BabySittingDto>>
    findFavoriteBabysitting(@RequestHeader(value = "token") String token,
                            @PageableDefault(value = 15, sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<BabySittingDto> data = favoriteService.findFavoriteBabysitting(accountDto, 1, pageable);
        ResponseResult<Page<BabySittingDto>> result = new ResponseResult<>(true, "query favorite babysitting success!", data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找喜欢的BabySitting列表")
    @RequestMapping(value = "/event/list", method = RequestMethod.GET)
    public ResponseResult<Page<EventDto>>
    findFavoriteEvent(@RequestHeader(value = "token") String token,
                      @PageableDefault(value = 15, sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        Page<EventDto> data = favoriteService.findFavoriteEvent(accountDto,2, pageable);
        ResponseResult<Page<EventDto>> result = new ResponseResult<>(true, "query favorite event success!", data, HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查找所有喜欢的列表")
    @RequestMapping(value = "/all/list", method = RequestMethod.GET)
    public ResponseResult<FindAllFavoriteDto>
    findAllFavorites(@RequestHeader(value = "token") String token,
                     @PageableDefault(value = 15, sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountDto accountDto = accountService.checkToken(token);
        FindAllFavoriteDto data = favoriteService.findAllFavoriteList(accountDto, pageable);
        ResponseResult<FindAllFavoriteDto> result = new ResponseResult<>(true, "query favorite all success!", data, HttpStatus.OK.value());
        return result;
    }

}
