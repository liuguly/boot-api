package com.three.sharecare.bootapi.service;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.repository.*;
import com.three.sharecare.bootapi.utils.CalulateTwoLanLon;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springside.modules.utils.collection.CollectionUtil;
import org.springside.modules.utils.mapper.JsonMapper;

import java.util.Date;
import java.util.List;

public abstract class BaseService {


    @Autowired
    protected ShareCareDao shareCareDao;

    @Autowired
    protected BabySittingDao babySittingDao;

    @Autowired
    protected EventDao eventDao;

    @Autowired
    protected FavoriteDao favoriteDao;

    @Autowired
    protected BookingDao bookingDao;

    @Autowired
    protected CreditCardDao creditCardDao;

    /**
     * json 工具
     */
    protected JsonMapper jsonMapper = new JsonMapper();

    private final Integer NEARBY = 0;
    private final Integer ANYWHERE = 1;
    private final Integer LOCATION = 2;

    List<ShareCareDto> getShareCareDtoList(AccountDto accountDto,
                                           SearchShareByConditionDto searchShareByConditionDto,
                                           Page<ShareCare> shareCares) {
        List<ShareCareDto> shareCareDtoList = Lists.newArrayList();
        Double addressLat = searchShareByConditionDto.getAddressLat();
        Double addressLon = searchShareByConditionDto.getAddressLon();
        Integer locationType = searchShareByConditionDto.getLocationType();
        if (NEARBY == locationType || LOCATION == locationType) {
            List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 0);
            String searchTime = searchShareByConditionDto.getTime();
            for (ShareCare shareCare : shareCares) {
                String sAddressLat = shareCare.getAddressLat();
                String sAddressLon = shareCare.getAddressLon();
                Double distance = CalulateTwoLanLon.getDistance(addressLat, addressLon, Double.parseDouble(sAddressLat), Double.parseDouble(sAddressLon));
                String avableTime = shareCare.getAvailableTime();
                //时间是空，并且距离小于5000
                if(StringUtils.isBlank(searchTime) && distance <= 5000){
                    ShareCareDto shareCareDto = copySingleShareCareDto(shareCare, favoriteList);
                    shareCareDtoList.add(shareCareDto);
                    //时间非空，并且距离小于5000，并且这个sharecare包含该时间
                }else if (StringUtils.isNotBlank(searchTime) && avableTime.contains(searchTime) && distance <= 5000) {
                    ShareCareDto shareCareDto = copySingleShareCareDto(shareCare, favoriteList);
                    shareCareDtoList.add(shareCareDto);
                }
            }
        }

        if (ANYWHERE == locationType) {
            List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 0);
            String searchTime = searchShareByConditionDto.getTime();
            for (ShareCare shareCare : shareCares) {
                String avableTime = shareCare.getAvailableTime();
                //时间是空
                if(StringUtils.isBlank(searchTime)){
                    ShareCareDto shareCareDto = copySingleShareCareDto(shareCare, favoriteList);
                    shareCareDtoList.add(shareCareDto);
                    //时间非空，并且这个sharecare包含该时间
                }else if (StringUtils.isNotBlank(searchTime) && avableTime.contains(searchTime)) {
                    ShareCareDto shareCareDto = copySingleShareCareDto(shareCare, favoriteList);
                    shareCareDtoList.add(shareCareDto);
                }
            }
        }

        return shareCareDtoList;
    }


    List<BabySittingDto> getBabySittingDtoList(AccountDto accountDto,
                                               SearchShareByConditionDto searchShareByConditionDto,
                                               Page<BabySitting> babySittings) {
        List<BabySittingDto> babySittingDtoList = Lists.newArrayList();
        Double addressLat = searchShareByConditionDto.getAddressLat();
        Double addressLon = searchShareByConditionDto.getAddressLon();
        Integer locationType = searchShareByConditionDto.getLocationType();
        if (NEARBY == locationType || LOCATION == locationType) {
            List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 1);
            String searchTime = searchShareByConditionDto.getTime();
            for (BabySitting babySitting : babySittings) {
                String bAddressLat = babySitting.getAddressLat();
                String bAddressLon = babySitting.getAddressLon();
                Date createTime = babySitting.getCreateTime();
                String screateTime = DateFormatUtils.format(createTime,"yyyy-MM-dd");
                Double distance = CalulateTwoLanLon.getDistance(addressLat, addressLon, Double.parseDouble(bAddressLat), Double.parseDouble(bAddressLon));
                if(StringUtils.isBlank(searchTime) && distance <= 5000){
                    BabySittingDto babySittingDto = copySingleBabySittingDto(babySitting, favoriteList);
                    babySittingDtoList.add(babySittingDto);
                }else if(StringUtils.isNotBlank(searchTime) && screateTime.equals(searchTime) && distance <= 5000){
                    BabySittingDto babySittingDto = copySingleBabySittingDto(babySitting, favoriteList);
                    babySittingDtoList.add(babySittingDto);
                }
            }
        }

        if (ANYWHERE == locationType) {
            List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 1);
            String searchTime = searchShareByConditionDto.getTime();
            for (BabySitting babySitting : babySittings) {
                Date createTime = babySitting.getCreateTime();
                String screateTime = DateFormatUtils.format(createTime,"yyyy-MM-dd");
                if(StringUtils.isBlank(searchTime)){
                    BabySittingDto babySittingDto = copySingleBabySittingDto(babySitting, favoriteList);
                    babySittingDtoList.add(babySittingDto);
                }else if(StringUtils.isNotBlank(searchTime) && screateTime.equals(searchTime)){
                    BabySittingDto babySittingDto = copySingleBabySittingDto(babySitting, favoriteList);
                    babySittingDtoList.add(babySittingDto);
                }
            }
        }
        return babySittingDtoList;
    }

    List<EventDto> getEventDtoList(AccountDto accountDto,
                                   SearchShareByConditionDto searchShareByConditionDto,
                                   Page<Event> events) {
        List<EventDto> eventDtoList = Lists.newArrayList();
        Double addressLat = searchShareByConditionDto.getAddressLat();
        Double addressLon = searchShareByConditionDto.getAddressLon();
        Integer locationType = searchShareByConditionDto.getLocationType();
        if (NEARBY == locationType || LOCATION == locationType) {
            List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 0);
            String searchTime = searchShareByConditionDto.getTime();
            for (Event event : events) {
                String sAddressLat = event.getWhereToMeetLat();
                String sAddressLon = event.getWhereToMeetLon();
                String startDate = event.getStartDate();
                Double distance = CalulateTwoLanLon.getDistance(addressLat, addressLon, Double.parseDouble(sAddressLat), Double.parseDouble(sAddressLon));
                if(StringUtils.isBlank(searchTime) && distance <= 5000){
                    EventDto eventDto = copySingleEventDto(event, favoriteList);
                    eventDtoList.add(eventDto);
                }else if(StringUtils.isNotBlank(searchTime) && startDate.equals(searchTime) && distance <= 5000){
                    EventDto eventDto = copySingleEventDto(event, favoriteList);
                    eventDtoList.add(eventDto);
                }
            }
        }

        if (ANYWHERE == locationType) {
            List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 0);
            String searchTime = searchShareByConditionDto.getTime();
            for (Event event : events) {
                String startDate = event.getStartDate();
                if(StringUtils.isBlank(searchTime)){
                    EventDto eventDto = copySingleEventDto(event, favoriteList);
                    eventDtoList.add(eventDto);
                }else if(StringUtils.isNotBlank(searchTime) && startDate.equals(searchTime)){
                    EventDto eventDto = copySingleEventDto(event, favoriteList);
                    eventDtoList.add(eventDto);
                }
            }
        }
        return eventDtoList;
    }


    protected List<ShareCareDto> copyShareCareProperty(Page<ShareCare> shareCares, AccountDto accountDto) {
        List<Favorite> shareFavorites = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 0);
        List<ShareCareDto> shareCareList = Lists.newArrayList();
        for (ShareCare shareCare : shareCares) {
            ShareCareDto shareCareDto = copySingleShareCareDto(shareCare, shareFavorites);
            shareCareList.add(shareCareDto);
        }
        return shareCareList;
    }

    protected ShareCareDto copySingleShareCareDto(ShareCare shareCare, List<Favorite> shareFavorites) {
        ShareCareDto shareCareDto = new ShareCareDto();
        BeanUtils.copyProperties(shareCare, shareCareDto);
        //封装图片路径
        JavaType javaType = jsonMapper.buildCollectionType(List.class, String.class);
        List<String> photoPahts = jsonMapper.fromJson(shareCare.getPhotosPath(), javaType);
        shareCareDto.setPhotosPath(photoPahts);
        Account account = shareCare.getOwner();
        if (account != null) {
            shareCareDto.setAccountId(account.getId());
            UserInfo userInfo = account.getUserInfo();
            if (userInfo != null) {
                String userIcon = userInfo.getUserIcon();
                shareCareDto.setUserName(StringUtils.isBlank(account.getUserName()) ? userInfo.getFullName() : account.getUserName());
                shareCareDto.setUserIcon(userIcon);
            }
        }
        for (Favorite favorite : shareFavorites) {
            Long shareCareId = shareCare.getId();
            if (shareCareId == favorite.getfTypeId()) {
                shareCareDto.setIsFavorite(favorite.getStatus());
                break;
            }
        }
        ReviewExtentionDto reviewExtentionDto = copyShareCareDtoProperty(shareCare);
        shareCareDto.setReviewDto(reviewExtentionDto);
        return shareCareDto;
    }


    protected List<BabySittingDto> copyBabysittingProperty(Page<BabySitting> babySittings, AccountDto accountDto) {
        List<Favorite> babySittingFavorites = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 1);
        List<BabySittingDto> babySittingDtoList = Lists.newArrayList();
        for (BabySitting babySitting : babySittings) {
            BabySittingDto babySittingDto = copySingleBabySittingDto(babySitting, babySittingFavorites);
            babySittingDtoList.add(babySittingDto);
        }
        return babySittingDtoList;
    }

    protected BabySittingDto copySingleBabySittingDto(BabySitting babySitting, List<Favorite> babySittingFavorites) {
        BabySittingDto babySittingDto = new BabySittingDto();
        BeanUtils.copyProperties(babySitting, babySittingDto);
        //封装图片路径
        JavaType javaType = jsonMapper.buildCollectionType(List.class, String.class);
        List<String> photoPahts = jsonMapper.fromJson(babySitting.getPhotosPath(), javaType);
        babySittingDto.setPhotosPath(photoPahts);
        Account account = babySitting.getOwner();
        if (account != null) {
            babySittingDto.setAccountId(account.getId());
            UserInfo userInfo = account.getUserInfo();
            if (userInfo != null) {
                String userIcon = userInfo.getUserIcon();
                babySittingDto.setUserIcon(userIcon);
                babySittingDto.setUserName(StringUtils.isBlank(account.getUserName()) ? userInfo.getFullName() : account.getUserName());
            }
        }
        for (Favorite favorite : babySittingFavorites) {
            Long babySittingId = babySitting.getId();
            if (babySittingId == favorite.getfTypeId()) {
                babySittingDto.setIsFavorite(favorite.getStatus());
                break;
            }
        }
        ReviewExtentionDto reviewExtentionDto = copyBabySittingDtoProperty(babySitting);
        babySittingDto.setReviewDto(reviewExtentionDto);
        return babySittingDto;
    }


    protected List<EventDto> copyEventProperty(Page<Event> events, AccountDto accountDto) {
        List<Favorite> eventFavorites = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 2);
        List<EventDto> eventDtoList = Lists.newArrayList();
        for (Event event : events) {
            EventDto eventDto = copySingleEventDto(event, eventFavorites);
            eventDtoList.add(eventDto);
        }
        return eventDtoList;
    }

    protected EventDto copySingleEventDto(Event event, List<Favorite> eventFavorites) {
        EventDto eventDto = new EventDto();
        BeanUtils.copyProperties(event, eventDto);
        String imagePath = event.getImagePath();
        JavaType javaType = jsonMapper.buildCollectionType(List.class, String.class);
        List<String> imagePathList = jsonMapper.fromJson(imagePath, javaType);
        eventDto.setImagePathList(imagePathList);
        Account account = event.getOwner();
        if (account != null) {
            eventDto.setAccountId(account.getId());
            UserInfo userInfo = account.getUserInfo();
            if (userInfo != null) {
                String userIcon = userInfo.getUserIcon();
                eventDto.setUserIcon(userIcon);
                eventDto.setUserName(StringUtils.isBlank(account.getUserName()) ? userInfo.getFullName() : account.getUserName());
            }
        }
        for (Favorite favorite : eventFavorites) {
            Long eventId = event.getId();
            if (eventId == favorite.getfTypeId()) {
                eventDto.setIsFavorite(favorite.getStatus());
                break;
            }
        }
        ReviewExtentionDto reviewExtentionDto = copyEventReviewDtoProperty(event);
        eventDto.setReviewDto(reviewExtentionDto);
        return eventDto;
    }

    protected ReviewExtentionDto copyEventReviewDtoProperty(Event event) {
        List<Review> reviews = event.getReviewList();
        ReviewExtentionDto reviewExtentionDto = foreachReviewDtoList(reviews);
        return reviewExtentionDto;
    }

    protected ReviewExtentionDto copyBabySittingDtoProperty(BabySitting babySitting) {
        List<Review> reviews = babySitting.getReviewList();
        ReviewExtentionDto reviewExtentionDto = foreachReviewDtoList(reviews);
        return reviewExtentionDto;
    }

    protected ReviewExtentionDto copyShareCareDtoProperty(ShareCare shareCare) {
        List<Review> reviews = shareCare.getReviewList();
        ReviewExtentionDto reviewExtentionDto = foreachReviewDtoList(reviews);
        return reviewExtentionDto;
    }

    protected ReviewExtentionDto foreachReviewDtoList(List<Review> reviews) {
        ReviewExtentionDto reviewExtentionDto = new ReviewExtentionDto();
        Integer starRating = 0;
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
            starRating += review.getStarRating() == null ? 0 : review.getStarRating();
        }
        reviewExtentionDto.setReviewDtoList(reviewDtos.size() > 3 ? reviewDtos.subList(reviewDtos.size() - 2, reviewDtos.size()) : reviewDtos);
        if (CollectionUtil.isNotEmpty(reviews)) {
            reviewExtentionDto.setReviewsCount(reviews.size());
            reviewExtentionDto.setTotalStarRating(starRating / reviews.size());
        }
        return reviewExtentionDto;
    }


}
