package com.three.sharecare.bootapi.service;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.repository.BabySittingDao;
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
public class BabySittingService extends BaseService{


    @Autowired
    private BabySittingDao babySittingDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 保存专职保姆信息
     * @param babySittingDto 专职保姆信息
     * @return 主键
     */
    public Long saveBabySitting(HttpServletRequest request, MultipartFile[] multipartFiles, BabySittingDto babySittingDto){
        String token = request.getHeader("token");
        AccountDto account = accountService.checkToken(token);
        String photoPaths = fileUploadService.getPhotoPaths(request,multipartFiles,account.getId()+"","babysitting");
        BabySitting babySitting = BeanMapper.map(babySittingDto, BabySitting.class);
        babySitting.setPhotosPath(photoPaths);
        babySitting.setCreateTime(new Date());
        babySitting.setUpdateTime(new Date());
        Account owner = BeanMapper.map(account, Account.class);
        babySitting.setOwner(owner);
        babySitting = babySittingDao.save(babySitting);
        return babySitting.getId();
    }

    /**
     * 分页结果
     * @param pageable 分页对象
     * @return 分页结果
     */
    @Transactional(readOnly = true)
    public Page<BabySittingDto> findBabySitting(AccountDto accountDto, SearchShareByConditionDto searchShareByConditionDto, Pageable pageable){
        Page<BabySitting> data = babySittingDao.findAll(pageable);
        List<BabySittingDto> babySittingDtoList = Lists.newArrayList();
        boolean conditionEnable = searchShareByConditionDto.isConditionEnable();
        if(conditionEnable){
            babySittingDtoList = getBabySittingDtoList(accountDto,searchShareByConditionDto,data);
        }else {
            babySittingDtoList = copyBabysittingProperty(data,accountDto);
        }
        PageImpl<BabySittingDto> result = new PageImpl<>(babySittingDtoList, pageable, data.getTotalElements());
        return result;
    }

    /**
     * 查看详情
     * @param id 主键
     * @return 一条记录
     */
    @Transactional(readOnly = true)
    public BabySittingDto findOneBabySitting(AccountDto accountDto,Long id){
        BabySitting babySitting = babySittingDao.findOne(id);
        List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(),1);
        BabySittingDto babySittingDto =copySingleBabySittingDto(babySitting,favoriteList);
        return babySittingDto;
    }


    @Transactional(readOnly = true)
    public Page<BabySittingDto> findBabySittingByMyself(AccountDto accountDto,Pageable pageable){
        List<Favorite> babySittingFavorites = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(),1);
        Long accountId = accountDto.getId();
        Page<BabySitting> data = babySittingDao.findAllByOwnerId(accountId,pageable);
        List<BabySittingDto> babySittingDtoList = Lists.newArrayList();
        for(BabySitting babySitting : data){
            BabySittingDto babySittingDto = new BabySittingDto();
            BeanUtils.copyProperties(babySitting, babySittingDto);
            //封装图片路径
            JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
            List<String> photoPahts = jsonMapper.fromJson(babySitting.getPhotosPath(), javaType);
            babySittingDto.setPhotosPath(photoPahts);
            Account account = babySitting.getOwner();
            if(account!=null){
                babySittingDto.setAccountId(account.getId());
                UserInfo userInfo = account.getUserInfo();
                if(userInfo!=null){
                    String userIcon = userInfo.getUserIcon();
                    babySittingDto.setUserIcon(userIcon);
                    babySittingDto.setUserName(StringUtils.isBlank(account.getUserName())?userInfo.getFullName():account.getUserName());
                }
            }
            for(Favorite favorite : babySittingFavorites){
                Long babySittingId = babySitting.getId();
                if(babySittingId == favorite.getfTypeId()){
                    babySittingDto.setIsFavorite(favorite.getStatus());
                    break;
                }
            }

            ReviewExtentionDto reviewExtentionDto = new ReviewExtentionDto();
            Integer starRating = 0;
            List<ReviewDto> reviewDtos = Lists.newArrayList();
            List<Review> reviews = babySitting.getReviewList();
            for(Review review : reviews){
                ReviewDto reviewDto = new ReviewDto();
                BeanUtils.copyProperties(review,reviewDto);
                Account reviewAccount = review.getAccount();
                UserInfo userInfo = reviewAccount.getUserInfo();
                if(userInfo!=null){
                    reviewDto.setUserIcon(userInfo.getUserIcon());
                    reviewDto.setUserName(StringUtils.isBlank(reviewAccount.getUserName())?userInfo.getFullName():reviewAccount.getUserName());
                }
                starRating +=review.getStarRating()==null ? 0 : review.getStarRating();
                reviewDtos.add(reviewDto);
            }
            reviewExtentionDto.setReviewDtoList(reviewDtos.size()>3?reviewDtos.subList(reviewDtos.size()-2,reviewDtos.size()):reviewDtos);
            if(CollectionUtil.isNotEmpty(reviews)){
                starRating = starRating/reviews.size();
                reviewExtentionDto.setReviewsCount(reviews.size());
                reviewExtentionDto.setTotalStarRating(starRating);
            }
            babySittingDto.setReviewDto(reviewExtentionDto);
            babySittingDtoList.add(babySittingDto);
        }
        Page<BabySittingDto> result = new PageImpl<BabySittingDto>(babySittingDtoList,pageable,data.getTotalElements());
        return result;
    }
}
