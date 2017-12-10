package com.three.sharecare.bootapi.api;

import com.three.sharecare.bootapi.api.support.ResponseResult;
import com.three.sharecare.bootapi.domain.Review;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.ReviewDto;
import com.three.sharecare.bootapi.service.AccountService;
import com.three.sharecare.bootapi.service.ReviewService;
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

@Api(value = "评论")
@RestController
@RequestMapping(value = "/v1/review")
public class ReviewEndPoint {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "增加评论",notes = "增加评论")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseResult<Long> addReview(@RequestHeader(value = "token") String token,
                                          @RequestBody @Valid ReviewDto reviewDto){
        AccountDto accountDto = accountService.checkToken(token);
        Review review = reviewService.addReview(accountDto,reviewDto);
        ResponseResult<Long> result = new ResponseResult<>(true,"add review success!",review.getId(), HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查看一条评论详情",notes = "查看一条评论详情")
    @RequestMapping(value = "/detail/{reviewId}",method = RequestMethod.GET)
    public ResponseResult<ReviewDto> findOneReviewDetail(@RequestHeader(value = "token") String token,
                                                         @PathVariable(value = "reviewId")Long reviewId){
        AccountDto accountDto = accountService.checkToken(token);
        ReviewDto data = reviewService.getReviewDetail(accountDto,reviewId);
        ResponseResult<ReviewDto> result = new ResponseResult<>(true,"get review detail success!",data,HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查看评论列表",notes = "查看评论列表")
    @RequestMapping(value = "/list/{reviewType}/{reviewTypeId}",method = RequestMethod.GET)
    public ResponseResult<Page<ReviewDto>> findReviewList(@RequestHeader(value = "token") String token,
                                                          @PathVariable(value = "reviewType")Integer reviewType,
                                                          @PathVariable(value = "reviewTypeId")Long reviewTypeId,
                                                          @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<ReviewDto> data = reviewService.findReviewList(accountDto,reviewType,reviewTypeId,pageable);
        ResponseResult<Page<ReviewDto>> result = new ResponseResult<>(true,"get review list success!",data,HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "查看别人评价我的列表",notes = "查看别人评价我的列表")
    @RequestMapping(value = "/others/reviewMe/{reviewType}",method = RequestMethod.GET)
    public ResponseResult<Page<ReviewDto>> findOthersReviewMe(@RequestHeader(value = "token") String token,
                                                              @PathVariable(value = "reviewType")Integer reviewType,
                                                              @PageableDefault(sort = {"create_time"}, direction = Sort.Direction.DESC) Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<ReviewDto> data = reviewService.findOthersReviewMe(accountDto,reviewType,pageable);
        ResponseResult<Page<ReviewDto>> result = new ResponseResult<>(true, "find other review me success!",data,HttpStatus.OK.value());
        return result;
    }


    @ApiOperation(value = "查看我评价别人的列表",notes = "查看我评价别人的列表")
    @RequestMapping(value = "/me/reviewOther/{reviewType}",method = RequestMethod.GET)
    public ResponseResult<Page<ReviewDto>> findMeReviewOthers(@RequestHeader(value = "token") String token,
                                                              @PathVariable(value = "reviewType")Integer reviewType,
                                                              @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable){
        AccountDto accountDto = accountService.checkToken(token);
        Page<ReviewDto> data = reviewService.findMeReviewOthers(accountDto,reviewType,pageable);
        ResponseResult<Page<ReviewDto>> result = new ResponseResult<>(true, "find me review others success!",data,HttpStatus.OK.value());
        return result;
    }

}
