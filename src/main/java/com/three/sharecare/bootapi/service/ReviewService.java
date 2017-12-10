package com.three.sharecare.bootapi.service;

import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.ReviewDto;
import com.three.sharecare.bootapi.repository.AccountDao;
import com.three.sharecare.bootapi.repository.ReviewDao;
import com.three.sharecare.bootapi.service.exception.ErrorCode;
import com.three.sharecare.bootapi.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.collection.CollectionUtil;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReviewService extends BaseService{

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private AccountDao accountDao;

    /**
     * 增加评论
     * @param accountDto 账户
     * @param reviewDto 评价内容
     * @return 实体
     */
    public Review addReview(AccountDto accountDto, ReviewDto reviewDto){
        Account account = accountDao.findOne(accountDto.getId());
        Review review = new Review();
        BeanUtils.copyProperties(reviewDto,review);
        review.setAccount(account);
        Integer reviewType = reviewDto.getReviewType();
        if(reviewType == 0 && null != reviewDto.getShareCareId()){
            Long shareCareId = reviewDto.getShareCareId();
            ShareCare shareCare = shareCareDao.findOne(shareCareId);
            review.setShareCare(shareCare);
        }else if(reviewType == 1 && null!=reviewDto.getBabySittingId()){
            Long babysittingId = reviewDto.getBabySittingId();
            BabySitting babySitting = babySittingDao.findOne(babysittingId);
            review.setBabySitting(babySitting);
        }else if(reviewType ==2 && null!=reviewDto.getEventId()){
            Long eventId = reviewDto.getEventId();
            Event event = eventDao.findOne(eventId);
            review.setEvent(event);
        }else{
            throw new ServiceException("the parameters is not exist! please try again", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        review.setCreateTime(new Date());
        review.setUpdateTime(new Date());
        reviewDao.save(review);
        return review;
    }

    /**
     * 查看评论详情
     * @param accountDto 当前账户
     * @param reviewId id
     * @return 评论
     */
    public ReviewDto getReviewDetail(AccountDto accountDto,Long reviewId){
        Review review = reviewDao.findOne(reviewId);
        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(review,reviewDto);

        Account account = review.getAccount();
        UserInfo userInfo = account.getUserInfo();
        if(userInfo!=null){
            reviewDto.setUserIcon(userInfo.getUserIcon());
            reviewDto.setUserName(userInfo.getFullName());
        }
        return reviewDto;
    }


    public Page<ReviewDto> findReviewList(AccountDto accountDto, Integer reviewType,
                                          Long typeId, Pageable pageable){
        Page<Review> data = new PageImpl<Review>(Lists.<Review>newArrayList());
        if(0==reviewType){
            data = reviewDao.findReviewsByReviewTypeAndShareCareId(reviewType,typeId,pageable);
        }else if(1==reviewType){
            data = reviewDao.findReviewsByReviewTypeAndBabySittingId(reviewType,typeId,pageable);
        }else if(2==reviewType){
            data = reviewDao.findReviewsByReviewTypeAndEventId(reviewType,typeId,pageable);
        }
        if(CollectionUtil.isEmpty(data.getContent())) return new PageImpl<ReviewDto>(Lists.<ReviewDto>newArrayList(),pageable,0);
        List<ReviewDto> result = Lists.newArrayList();
        for(Review review : data){
            ReviewDto reviewDto = new ReviewDto();
            BeanUtils.copyProperties(review,reviewDto);

            Account account = review.getAccount();
            UserInfo userInfo = account.getUserInfo();
            if(userInfo!=null){
                reviewDto.setUserIcon(userInfo.getUserIcon());
                reviewDto.setUserName(StringUtils.isBlank(account.getUserName())?userInfo.getFullName():account.getUserName());
            }
            result.add(reviewDto);
        }
        Page<ReviewDto> reviewDtoPage = new PageImpl<ReviewDto>(result,pageable,data.getTotalElements());
        return reviewDtoPage;
    }

    /**
     * 查找别人评价我的
     * @param accountDto 当前账户
     * @param reviewType 类型
     * @param pageable 分页
     * @return 分页结果
     */
    public Page<ReviewDto> findOthersReviewMe(AccountDto accountDto, Integer reviewType, Pageable pageable){
        Long accoutId = accountDto.getId();
        Page<Review> reviews = null;
        if(reviewType == 0){
            reviews = reviewDao.findMeShareCareReviews(accoutId, pageable);
        }else if(reviewType == 1){
            reviews = reviewDao.findMeBabysittingReviews(accoutId, pageable);
        }else if(reviewType == 2){
            reviews = reviewDao.findMeEventReviewsByMe(accoutId, pageable);
        }
        List<ReviewDto> reviewDtos = Lists.newArrayList();
        for (Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto();
            BeanUtils.copyProperties(review, reviewDto);
            Account reviewAccount = review.getAccount();
            UserInfo userInfo = reviewAccount.getUserInfo();
            if (userInfo != null) {
                reviewDto.setUserIcon(userInfo.getUserIcon());
                reviewDto.setUserName(StringUtils.isBlank(reviewAccount.getUserName()) ? userInfo.getFullName() : reviewAccount.getUserName());
            }
            reviewDtos.add(reviewDto);
        }
        Page<ReviewDto> reviewDtoPage = new PageImpl<ReviewDto>(reviewDtos,pageable,reviews.getTotalElements());
        return reviewDtoPage;
    }

    /**
     * 查找我评价别人的
     * @param accountDto 当前账户
     * @param reviewType 评论类型
     * @param pageable 分页
     * @return 分页结果
     */
    public Page<ReviewDto> findMeReviewOthers(AccountDto accountDto , Integer reviewType, Pageable pageable){
        Long accountId = accountDto.getId();
        Page<Review> reviewPage = reviewDao.findReviewsByAccount_IdAndReviewType(accountId,reviewType,pageable);
        List<ReviewDto> reviewDtos = Lists.newArrayList();
        for (Review review : reviewPage) {
            ReviewDto reviewDto = new ReviewDto();
            BeanUtils.copyProperties(review, reviewDto);
            Account reviewAccount = review.getAccount();
            UserInfo userInfo = reviewAccount.getUserInfo();
            if (userInfo != null) {
                reviewDto.setUserIcon(userInfo.getUserIcon());
                reviewDto.setUserName(StringUtils.isBlank(reviewAccount.getUserName()) ? userInfo.getFullName() : reviewAccount.getUserName());
            }
            reviewDtos.add(reviewDto);
        }
        Page<ReviewDto> reviewDtoPage = new PageImpl<>(reviewDtos,pageable,reviewPage.getTotalElements());
        return reviewDtoPage;
    }

}
