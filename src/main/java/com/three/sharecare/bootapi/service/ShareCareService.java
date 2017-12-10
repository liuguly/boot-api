package com.three.sharecare.bootapi.service;

import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.mapper.BeanMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ShareCareService extends BaseService{



    private static final Logger LOGGER = LoggerFactory.getLogger(ShareCareService.class);

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private AccountService accountService;

    /**
     * 保存share care
     *
     * @param shareCareDto 实体
     * @return 主键
     */
    public Long saveShareCare(HttpServletRequest request, MultipartFile[] multipartFiles, ShareCareDto shareCareDto) {
        String token = request.getHeader("token");
        AccountDto account = accountService.checkToken(token);
        String photoPaths = fileUploadService.getPhotoPaths(request, multipartFiles, account.getId() + "", "sharecare");
        ShareCare shareCare = BeanMapper.map(shareCareDto, ShareCare.class);
        shareCare.setPhotosPath(photoPaths);
        shareCare.setCreateTime(new Date());
        shareCare.setUpdateTime(new Date());
        Account owner = BeanMapper.map(account, Account.class);
        shareCare.setOwner(owner);
        shareCare = shareCareDao.save(shareCare);
        return shareCare.getId();
    }


    /**
     * 分页查询所有发布消息
     *
     * @param pageable 分页对象
     * @return 分页结果
     */
    @Transactional(readOnly = true)
    public Page<ShareCareDto> findShareCareList(AccountDto accountDto,SearchShareByConditionDto searchShareByConditionDto, Pageable pageable) {
        List<ShareCareDto> shareCareDtoList = Lists.newArrayList();
        Page<ShareCare> data = shareCareDao.findAll(pageable);
        boolean conditionEnable = searchShareByConditionDto.isConditionEnable();
        if(conditionEnable){
            shareCareDtoList = getShareCareDtoList(accountDto,searchShareByConditionDto,data);
        }else{
            shareCareDtoList = copyShareCareProperty(data,accountDto);
        }
        PageImpl<ShareCareDto> result = new PageImpl<>(shareCareDtoList, pageable, data.getTotalElements());
        return result;
    }

    public ShareCareDto findOneShareCare(AccountDto accountDto, Long shareCareId){
        ShareCare shareCare = shareCareDao.findOne(shareCareId);
        List<Favorite> favoriteList = favoriteDao.findAllByAccount_IdAndFType(accountDto.getId(), 0);
        ShareCareDto shareCareDto = copySingleShareCareDto(shareCare,favoriteList);
        return shareCareDto;
    }


    @Transactional(readOnly = true)
    public FindAllShareDto searchAllShare(AccountDto accountDto, SearchShareByConditionDto searchShareByConditionDto, Pageable pageable) {
        FindAllShareDto findAllShareDto = new FindAllShareDto();

        Integer careType = searchShareByConditionDto.getCareType();
        if(careType == 0 ){
            Page<ShareCare> data = shareCareDao.findAll(pageable);
            List<ShareCareDto> shareCareDtoList = getShareCareDtoList(accountDto,searchShareByConditionDto,data);
            findAllShareDto.setShareCareList(shareCareDtoList);
        }else if(careType == 1){
            Page<BabySitting> data = babySittingDao.findAll(pageable);
            List<BabySittingDto> babySittingList = getBabySittingDtoList(accountDto,searchShareByConditionDto,data);
            findAllShareDto.setBabySittingList(babySittingList);
        }else if(careType == 2){
            Page<Event> data = eventDao.findAll(pageable);
            List<EventDto> eventDtoList = getEventDtoList(accountDto,searchShareByConditionDto,data);
            findAllShareDto.setEventList(eventDtoList);
        }
        return findAllShareDto;
    }


    @Transactional(readOnly = true)
    public FindAllShareDto findAllShare(AccountDto accountDto, SearchShareByConditionDto searchShareByConditionDto,Pageable pageable) {
        Page<ShareCare> shareCares = shareCareDao.findAll(pageable);
        Page<BabySitting> babySittings = babySittingDao.findAll(pageable);
        Page<Event> events = eventDao.findAll(pageable);

        List<ShareCareDto> shareCareList = copyShareCareProperty(shareCares,accountDto);
        List<BabySittingDto> babySittingList = copyBabysittingProperty(babySittings,accountDto);
        List<EventDto> eventList = copyEventProperty(events,accountDto);

        FindAllShareDto findAllShareDto = new FindAllShareDto();
        findAllShareDto.setBabySittingList(babySittingList);
        findAllShareDto.setEventList(eventList);
        findAllShareDto.setShareCareList(shareCareList);
        return findAllShareDto;
    }

    /**
     * 获取自己发布的sharecare列表
     *
     * @param accountDto 当前账户
     * @param pageable   分页
     * @return 分页结果
     */
    public Page<ShareCareDto> findSharecareListByMyself(AccountDto accountDto, Pageable pageable) {
        Long ownerId = accountDto.getId();
        Page<ShareCare> data = shareCareDao.findAllByOwnerId(ownerId, pageable);
        List<ShareCareDto> shareCareDtoList = copyShareCareProperty(data,accountDto);
        Page<ShareCareDto> result = new PageImpl<>(shareCareDtoList, pageable, shareCareDtoList.size());
        return result;
    }

}
