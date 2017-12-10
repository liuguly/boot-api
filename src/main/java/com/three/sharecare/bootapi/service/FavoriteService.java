package com.three.sharecare.bootapi.service;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.collection.CollectionUtil;
import org.springside.modules.utils.mapper.JsonMapper;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FavoriteService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private FavoriteDao favoriteDao;

    private JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    private ShareCareDao shareCareDao;

    @Autowired
    private BabySittingDao babySittingDao;

    @Autowired
    private EventDao eventDao;

    /**
     * 点赞或者取消
     *
     * @param accountDto 当前用户
     * @param request    请求
     * @return 当前实体
     */
    public FavoriteDto saveFavorite(AccountDto accountDto, FavoriteRequest request) {
        Account account = accountDao.findOne(accountDto.getId());
        Favorite favorite = new Favorite();
        BeanUtils.copyProperties(request, favorite);
        favorite.setAccount(account);
        favorite.setCreateTime(new Date());
        favorite.setUpdateTime(new Date());
        favoriteDao.save(favorite);
        FavoriteDto favoriteDto = new FavoriteDto();
        BeanUtils.copyProperties(favorite, favoriteDto);
        return favoriteDto;
    }

    /**
     * 获取喜欢的分享列表
     *
     * @param accountDto 当前用户
     * @param careType   0-> sharecare  1->babysitting   2->event
     * @param pageable   分页
     * @return 分页结果
     */
    public Page<ShareCareDto> findFavoriteSharecare(AccountDto accountDto, Integer careType, Pageable pageable) {
        Long accountId = accountDto.getId();
        Page<ShareCare> data = shareCareDao.findFavoriteShareCare(accountId, careType, pageable);
        List<ShareCareDto> shareCareDtoList = Lists.newArrayList();
        for (ShareCare shareCare : data) {
            ShareCareDto shareCareDto = new ShareCareDto();
            BeanUtils.copyProperties(shareCare, shareCareDto);
            //封装图片路径
            JavaType javaType = jsonMapper.buildCollectionType(List.class, String.class);
            List<String> photoPahts = jsonMapper.fromJson(shareCare.getPhotosPath(), javaType);
            shareCareDto.setPhotosPath(photoPahts);
            Account account = shareCare.getOwner();
            if (account != null) {
                UserInfo userInfo = account.getUserInfo();
                if (userInfo != null) {
                    String userIcon = userInfo.getUserIcon();
                    shareCareDto.setUserIcon(userIcon);
                    shareCareDto.setUserName(StringUtils.isBlank(account.getUserName())?userInfo.getFullName():account.getUserName());
                }
            }
            ReviewExtentionDto reviewExtentionDto = new ReviewExtentionDto();
            Integer starRating = 0;
            List<ReviewDto> reviewDtos = Lists.newArrayList();
            List<Review> reviews = shareCare.getReviewList();
            int count =0;
            for(Review review : reviews){
                if(count==2) break;
                ReviewDto reviewDto = new ReviewDto();
                BeanUtils.copyProperties(review,reviewDto);
                Account reviewAccount = review.getAccount();
                UserInfo userInfo = reviewAccount.getUserInfo();
                if(userInfo!=null){
                    reviewDto.setUserIcon(userInfo.getUserIcon());
                    reviewDto.setUserName(StringUtils.isBlank(reviewAccount.getUserName())?userInfo.getFullName():reviewAccount.getUserName());
                }
                reviewDtos.add(reviewDto);
                starRating +=review.getStarRating()==null ? 0 : review.getStarRating();
                count++;
            }
            reviewExtentionDto.setReviewDtoList(reviewDtos);
            if(CollectionUtil.isNotEmpty(reviews)){
               reviewExtentionDto.setTotalStarRating(starRating/reviews.size());
            }
            shareCareDto.setIsFavorite(1);
            shareCareDto.setReviewDto(reviewExtentionDto);
            shareCareDtoList.add(shareCareDto);
        }
        PageImpl<ShareCareDto> result = new PageImpl<>(shareCareDtoList, pageable, data.getTotalElements());
        return result;
    }

    /**
     * 获取喜欢的babysitting列表
     *
     * @param accountDto 当前用户
     * @param careType   0-> sharecare  1->babysitting   2->event
     * @param pageable   分页
     * @return 分页结果
     */
    public Page<BabySittingDto> findFavoriteBabysitting(AccountDto accountDto, Integer careType, Pageable pageable) {
        Long accountId = accountDto.getId();
        Page<BabySitting> data = babySittingDao.findFavoriteBabySitting(accountId, careType, pageable);
        List<BabySittingDto> babySittingDtoList = Lists.newArrayList();
        for (BabySitting babySitting : data) {
            BabySittingDto babySittingDto = new BabySittingDto();
            BeanUtils.copyProperties(babySitting, babySittingDto);
            //封装图片路径
            JavaType javaType = jsonMapper.buildCollectionType(List.class, String.class);
            List<String> photoPahts = jsonMapper.fromJson(babySitting.getPhotosPath(), javaType);
            babySittingDto.setPhotosPath(photoPahts);
            Account account = babySitting.getOwner();
            if (account != null) {
                UserInfo userInfo = account.getUserInfo();
                if (userInfo != null) {
                    String userIcon = userInfo.getUserIcon();
                    babySittingDto.setUserIcon(userIcon);
                    babySittingDto.setUserName(StringUtils.isBlank(account.getUserName())?userInfo.getFullName():account.getUserName());
                }
            }
            ReviewExtentionDto reviewExtentionDto = new ReviewExtentionDto();
            Integer starRating = 0;
            List<ReviewDto> reviewDtos = Lists.newArrayList();
            List<Review> reviews = babySitting.getReviewList();
            int count =0;
            for(Review review : reviews){
                if(count==2) break;
                ReviewDto reviewDto = new ReviewDto();
                BeanUtils.copyProperties(review,reviewDto);
                Account reviewAccount = review.getAccount();
                UserInfo userInfo = reviewAccount.getUserInfo();
                if(userInfo!=null){
                    reviewDto.setUserIcon(userInfo.getUserIcon());
                    reviewDto.setUserName(StringUtils.isBlank(reviewAccount.getUserName())?userInfo.getFullName():reviewAccount.getUserName());
                }
                reviewDtos.add(reviewDto);
                starRating +=review.getStarRating()==null ? 0 : review.getStarRating();
                count++;
            }
            reviewExtentionDto.setReviewDtoList(reviewDtos);
            if(CollectionUtil.isNotEmpty(reviews)){
                reviewExtentionDto.setTotalStarRating(starRating/reviews.size());
            }
            babySittingDto.setIsFavorite(1);
            babySittingDto.setReviewDto(reviewExtentionDto);
            babySittingDtoList.add(babySittingDto);
        }
        PageImpl<BabySittingDto> result = new PageImpl<>(babySittingDtoList, pageable, data.getTotalElements());
        return result;
    }

    /**
     * 获取喜欢的Event列表
     *
     * @param accountDto 当前用户
     * @param careType   0-> sharecare  1->babysitting   2->event
     * @param pageable   分页
     * @return 分页结果
     */
    public Page<EventDto> findFavoriteEvent(AccountDto accountDto, Integer careType, Pageable pageable) {
        Long accountId = accountDto.getId();
        Page<Event> data = eventDao.findFavoriteEvent(accountId, careType, pageable);
        List<EventDto> result = Lists.newArrayList();
        for (Event event : data) {
            EventDto eventDto = new EventDto();
            BeanUtils.copyProperties(event, eventDto);
            String imagePath = event.getImagePath();
            JavaType javaType = jsonMapper.buildCollectionType(List.class, String.class);
            List<String> imagePathList = jsonMapper.fromJson(imagePath, javaType);
            eventDto.setImagePathList(imagePathList);
            Account account = event.getOwner();
            if (account != null) {
                UserInfo userInfo = account.getUserInfo();
                if (userInfo != null) {
                    String userIcon = userInfo.getUserIcon();
                    eventDto.setUserIcon(userIcon);
                    eventDto.setUserName(StringUtils.isBlank(account.getUserName())?userInfo.getFullName():account.getUserName());
                }
            }

            ReviewExtentionDto reviewExtentionDto = new ReviewExtentionDto();
            Integer starRating = 0;
            List<ReviewDto> reviewDtos = Lists.newArrayList();
            List<Review> reviews = event.getReviewList();
            int count =0;
            for(Review review : reviews){
                if(count==2) break;
                ReviewDto reviewDto = new ReviewDto();
                BeanUtils.copyProperties(review,reviewDto);
                Account reviewAccount = review.getAccount();
                UserInfo userInfo = reviewAccount.getUserInfo();
                if(userInfo!=null){
                    reviewDto.setUserIcon(userInfo.getUserIcon());
                    reviewDto.setUserName(StringUtils.isBlank(reviewAccount.getUserName())?userInfo.getFullName():reviewAccount.getUserName());
                }
                reviewDtos.add(reviewDto);
                starRating +=review.getStarRating()==null ? 0 : review.getStarRating();
                count++;
            }
            reviewExtentionDto.setReviewDtoList(reviewDtos);
            if(CollectionUtil.isNotEmpty(reviews)){
                reviewExtentionDto.setTotalStarRating(starRating/reviews.size());
            }
            eventDto.setIsFavorite(1);
            eventDto.setReviewDto(reviewExtentionDto);
            result.add(eventDto);
        }
        PageImpl<EventDto> page = new PageImpl<>(result, pageable, data.getTotalElements());
        return page;
    }

    /**
     * 查找喜欢的所有列表
     *
     * @param accountDto 当前账户
     * @param pageable   分页
     * @return 喜欢的结果集
     */
    public FindAllFavoriteDto findAllFavoriteList(AccountDto accountDto, Pageable pageable) {
        Page<ShareCareDto> shareCareDtoPage = findFavoriteSharecare(accountDto, 0, pageable);
        Page<BabySittingDto> babySittingPage = findFavoriteBabysitting(accountDto, 1, pageable);
        Page<EventDto> eventDtoPage = findFavoriteEvent(accountDto, 2, pageable);

        FindAllFavoriteDto findAllFavoriteDto = new FindAllFavoriteDto();
        PageImpl babySittingImpl = (PageImpl) babySittingPage;
        findAllFavoriteDto.setFavoriteBabysittingList(babySittingImpl == null ? Lists.<BabySittingDto>newArrayList() : babySittingImpl.getContent());
        PageImpl shareCareImpl = (PageImpl) shareCareDtoPage;
        findAllFavoriteDto.setFavoriteShareCareList(shareCareImpl == null ? Lists.<ShareCareDto>newArrayList() : shareCareImpl.getContent());
        PageImpl eventImpl = (PageImpl) eventDtoPage;
        findAllFavoriteDto.setFavoriteEventList(eventImpl == null ? Lists.<EventDto>newArrayList() : eventImpl.getContent());

        return findAllFavoriteDto;
    }


}
