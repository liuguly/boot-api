package com.three.sharecare.bootapi.service;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.collection.CollectionUtil;
import org.springside.modules.utils.mapper.BeanMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EventService extends BaseService{


    @Autowired
    private AccountService accountService;

    @Autowired
    private FileUploadService fileUploadService;


    /**
     * 发布事件
     * @param eventDto 发布事件详情
     * @return 主键
     */
    public Long saveEvent(HttpServletRequest request, MultipartFile[] multipartFiles, EventDto eventDto){
        String token = request.getHeader("token");
        AccountDto account = accountService.checkToken(token);
        String imagePath = fileUploadService.getPhotoPaths(request,multipartFiles,account.getId()+"","event");

        Event event = new Event();
        BeanUtils.copyProperties(eventDto,event);
        event.setImagePath(imagePath);
        event.setCreateTime(new Date());
        event.setUpdateTime(new Date());
        Account owner = BeanMapper.map(account, Account.class);
        event.setOwner(owner);
        event = eventDao.save(event);
        return event.getId();
    }

    /**
     * 分页查询所有发布事件
     * @param pageable 分页对象
     * @return 事件集合
     */
    public Page<EventDto> findEventList(AccountDto accountDto,SearchShareByConditionDto searchShareByConditionDto, Pageable pageable){
        Page<Event> data = eventDao.findAll(pageable);
        List<EventDto> result = Lists.newArrayList();
        boolean conditionEnable = searchShareByConditionDto.isConditionEnable();
        if(conditionEnable){
            result = getEventDtoList(accountDto,searchShareByConditionDto,data);
        }else {
            result = copyEventProperty(data,accountDto);
        }
        PageImpl<EventDto> page = new PageImpl<>(result,pageable,data.getTotalElements());
        return page;
    }

    public EventDto findOneEvent(AccountDto accountDto,Long eventId){
        Event event = eventDao.findOne(eventId);
        List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(),2);
        EventDto eventDto = copySingleEventDto(event,favoriteList);
        return eventDto;
    }


    /**
     * 分页查询自己发布事件
     * @param pageable 分页对象
     * @return 事件集合
     */
    public Page<EventDto> findEventListByMyself(AccountDto accountDto,Pageable pageable){
        List<Favorite> eventFavorites = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(),2);
        Long accountId = accountDto.getId();
        Page<Event> data = eventDao.findAllByOwnerId(accountId,pageable);
        List<EventDto> result = Lists.newArrayList();
        for(Event event : data){
            EventDto eventDto = new EventDto();
            BeanUtils.copyProperties(event,eventDto);
            String imagePath = event.getImagePath();
            JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
            List<String> imagePathList = jsonMapper.fromJson(imagePath, javaType);
            eventDto.setImagePathList(imagePathList);
            Account account = event.getOwner();
            if(account!=null){
                eventDto.setAccountId(account.getId());
                UserInfo userInfo = account.getUserInfo();
                if(userInfo!=null){
                    String userIcon = userInfo.getUserIcon();
                    eventDto.setUserIcon(userIcon);
                    eventDto.setUserName(StringUtils.isBlank(account.getUserName())?userInfo.getFullName():account.getUserName());
                }
            }
            for(Favorite favorite : eventFavorites){
                Long eventId = event.getId();
                if(eventId == favorite.getfTypeId()){
                    eventDto.setIsFavorite(favorite.getStatus());
                    break;
                }
            }

            ReviewExtentionDto reviewExtentionDto = new ReviewExtentionDto();
            Integer starRating = 0;
            List<ReviewDto> reviewDtos = Lists.newArrayList();
            List<Review> reviews = event.getReviewList();
            for(Review review : reviews){
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
            }
            reviewExtentionDto.setReviewDtoList(reviewDtos.size()>3?reviewDtos.subList(reviewDtos.size()-2,reviewDtos.size()):reviewDtos);
            if(CollectionUtil.isNotEmpty(reviews)){
                reviewExtentionDto.setReviewsCount(reviews.size());
                reviewExtentionDto.setTotalStarRating(starRating/reviews.size());
            }
            eventDto.setReviewDto(reviewExtentionDto);

            result.add(eventDto);
        }

        Page<EventDto> fResult = new PageImpl<>(result,pageable,data.getTotalElements());
        return fResult;
    }

}
