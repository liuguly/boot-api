package com.three.sharecare.bootapi.service;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.*;
import com.three.sharecare.bootapi.dto.*;
import com.three.sharecare.bootapi.repository.*;
import com.three.sharecare.bootapi.utils.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.mapper.JsonMapper;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookingService {


    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private ShareCareDao shareCareDao;

    @Autowired
    private BabySittingDao babySittingDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private PaymentDao paymentDao;

    /**
     * json 工具
     */
    private JsonMapper jsonMapper = new JsonMapper();

    /**
     * 预定一个服务
     * @param accountDto token
     * @param request 请求体
     * @return booking
     */
    public Booking bookingCare(AccountDto accountDto, BookingRequest request){
        Booking booking = new Booking();
        booking.setTypeId(request.getCareId());
        booking.setBookingCode(UUIDUtils.getUUID());
        booking.setCareType(request.getCareType());
        BeanUtils.copyProperties(request,booking);
        String whoIsComing = jsonMapper.toJson(request.getWhoIsComings());
        booking.setWhoIsComings(whoIsComing);
        Account account = new Account();
        BeanUtils.copyProperties(accountDto,account);
        booking.setAccount(account);
        booking.setBookingStatus(0);
        booking.setCreateTime(new Date());
        booking.setUpdateTime(new Date());
        booking = bookingDao.save(booking);
        return booking;
    }

    /**
     * 获取自己的预定列表
     * @return 预定列表
     */
    public Page<BookingDto> getBookingList(AccountDto accountDto , Integer shareType, Pageable pageable){
        Page<Booking> data = new PageImpl<Booking>(Lists.<Booking>newArrayList(),pageable,0);
        if(shareType == null){
            data = bookingDao.findAllByAccount_Id(accountDto.getId(),pageable);
        }else{
            data = bookingDao.findByCareTypeAndAccount_Id(shareType,accountDto.getId(),pageable);
        }
        List<BookingDto> result = Lists.newArrayList();
        for(Booking booking : data){
            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            Long typeId = booking.getTypeId();
            Integer type = booking.getCareType();
            if(type ==0){
                ShareCare sharecare = shareCareDao.findOne(typeId);
                if(sharecare!=null){
                    bookingDto.setShareAddress(sharecare.getAddress());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(sharecare.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    bookingDto.setAddressLat(sharecare.getAddressLat());
                    bookingDto.setAddressLon(sharecare.getAddressLon());
                }
            }else if(type ==1){
                BabySitting babySitting = babySittingDao.findOne(typeId);
                if(babySitting !=null){
//                    bookingDto.setShareAddress();
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(babySitting.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
//                    bookingDto.setAddressLat(babySittin);
//                    bookingDto.setAddressLon(sharecare.getAddressLon());
                }
            }else if(type==2){
                Event event = eventDao.findOne(typeId);
                if(event!=null){
                    bookingDto.setShareAddress(event.getAddress());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(event.getImagePath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    bookingDto.setAddressLat(event.getAddressLat());
                    bookingDto.setAddressLon(event.getAddressLon());
                    bookingDto.setWhereToMeetLat(event.getWhereToMeetLat());
                    bookingDto.setWhereToMeetLon(event.getWhereToMeetLon());
                }
            }
            JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
            List<UserInfoDto.ChildrenInfo> whoIsComings = jsonMapper.fromJson(booking.getWhoIsComings(),javaType);
            bookingDto.setWhoIsComings(whoIsComings);
            result.add(bookingDto);
        }
        Page<BookingDto> pageResult = new PageImpl<BookingDto>(result,pageable,result.size());
        return pageResult;
    }


    /**
     * 获取别人预定自己发布信息的列表
     * @return 预定列表
     */
    public Page<BookingDto> getBookingListByOthersBookingMe(AccountDto accountDto , Integer shareType, Pageable pageable){
        Page<Booking> data = new PageImpl<Booking>(Lists.<Booking>newArrayList());
        Long accountId = accountDto.getId();
        if(shareType ==0){
            data = bookingDao.findByOthersBookingMeSharecare(accountId,pageable);
        }else if(shareType ==1){
            data = bookingDao.findByOthersBookingMeBabysitting(accountId,pageable);
        }else if(shareType ==2){
            data = bookingDao.findByOthersBookingMeEvent(accountId,pageable);
        }
        List<BookingDto> result = Lists.newArrayList();
        for(Booking booking : data){
            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            Long typeId = booking.getTypeId();
            Integer type = booking.getCareType();
            if(type ==0){
                ShareCare sharecare = shareCareDao.findOne(typeId);
                if(sharecare!=null){
                    bookingDto.setShareAddress(sharecare.getAddress());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(sharecare.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    bookingDto.setAddressLat(sharecare.getAddressLat());
                    bookingDto.setAddressLon(sharecare.getAddressLon());
                }
            }else if(type ==1){
                BabySitting babySitting = babySittingDao.findOne(typeId);
                if(babySitting !=null){
//                    bookingDto.setShareAddress();
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(babySitting.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                }
            }else if(type==2){
                Event event = eventDao.findOne(typeId);
                if(event!=null){
                    bookingDto.setShareAddress(event.getAddress());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(event.getImagePath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    bookingDto.setAddressLat(event.getAddressLat());
                    bookingDto.setAddressLon(event.getAddressLon());
                    bookingDto.setWhereToMeetLat(event.getWhereToMeetLat());
                    bookingDto.setWhereToMeetLon(event.getWhereToMeetLon());
                }
            }
            JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
            List<UserInfoDto.ChildrenInfo> whoIsComings = jsonMapper.fromJson(booking.getWhoIsComings(),javaType);
            bookingDto.setWhoIsComings(whoIsComings);
            result.add(bookingDto);
        }
        Page<BookingDto> pageResult = new PageImpl<BookingDto>(result,pageable,result.size());
        return pageResult;
    }


    /**
     * 获取past预定信息
     * @param accountDto 当前用户
     * @param pageable 分页
     * @return booking
     */
    public Page<BookingDto> findPastBooking(AccountDto accountDto,Pageable pageable){
        Long accountId = accountDto.getId();
        Page<Booking> data = bookingDao.findAllByAccount_IdAndStartDateBefore(accountId,new Date(),pageable);
        List<BookingDto> result = Lists.newArrayList();
        for(Booking booking : data){
            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            Long typeId = booking.getTypeId();
            Integer type = booking.getCareType();
            if(type ==0){
                ShareCare sharecare = shareCareDao.findOne(typeId);
                if(sharecare!=null){
                    bookingDto.setShareAddress(sharecare.getAddress());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(sharecare.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    bookingDto.setAddressLat(sharecare.getAddressLat());
                    bookingDto.setAddressLon(sharecare.getAddressLon());
                    Account owner = sharecare.getOwner();
                    if(owner!=null){
                        UserInfo userInfo = owner.getUserInfo();
                        bookingDto.setUserName(owner.getUserName());
                        if(userInfo!=null){
                            bookingDto.setUserIcon(userInfo.getUserIcon());
                        }
                    }
                }
            }else if(type ==1){
                BabySitting babySitting = babySittingDao.findOne(typeId);
                if(babySitting !=null){
//                    bookingDto.setShareAddress();
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(babySitting.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    Account owner = babySitting.getOwner();
                    if(owner!=null){
                        UserInfo userInfo = owner.getUserInfo();
                        bookingDto.setUserName(owner.getUserName());
                        if(userInfo!=null){
                            bookingDto.setUserIcon(userInfo.getUserIcon());
                        }
                    }
                }
            }else if(type==2){
                Event event = eventDao.findOne(typeId);
                if(event!=null){
                    bookingDto.setShareAddress(event.getAddress());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(event.getImagePath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    bookingDto.setAddressLat(event.getAddressLat());
                    bookingDto.setAddressLon(event.getAddressLon());
                    bookingDto.setWhereToMeetLon(event.getWhereToMeetLon());
                    bookingDto.setWhereToMeetLat(event.getWhereToMeetLat());
                    Account owner = event.getOwner();
                    if(owner!=null){
                        UserInfo userInfo = owner.getUserInfo();
                        bookingDto.setUserName(owner.getUserName());
                        if(userInfo!=null){
                            bookingDto.setUserIcon(userInfo.getUserIcon());
                        }
                    }
                }
            }
            JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
            List<UserInfoDto.ChildrenInfo> whoIsComings = jsonMapper.fromJson(booking.getWhoIsComings(),javaType);
            bookingDto.setWhoIsComings(whoIsComings);
            result.add(bookingDto);
        }
        Page<BookingDto> pageResult = new PageImpl<BookingDto>(result,pageable,data.getTotalElements());
        return pageResult;
    }


    /**
     * 获取comming预定信息
     * @param accountDto 当前用户
     * @param pageable 分页
     * @return booking
     */
    public Page<BookingDto> findUpCommingBooking(AccountDto accountDto,Pageable pageable){
        Long accountId = accountDto.getId();
        Page<Booking> data = bookingDao.findUpcomingPaymentBookins(accountId,pageable);
        List<BookingDto> result = Lists.newArrayList();
        for(Booking booking : data){
            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            Long typeId = booking.getTypeId();
            Integer type = booking.getCareType();
            if(type ==0){
                ShareCare sharecare = shareCareDao.findOne(typeId);
                if(sharecare!=null){
                    bookingDto.setShareAddress(sharecare.getAddress());
                    bookingDto.setAddressLat(sharecare.getAddressLat());
                    bookingDto.setAddressLon(sharecare.getAddressLon());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(sharecare.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    Account owner = sharecare.getOwner();
                    if(owner!=null){
                        UserInfo userInfo = owner.getUserInfo();
                        bookingDto.setUserName(owner.getUserName());
                        if(userInfo!=null){
                            bookingDto.setUserIcon(userInfo.getUserIcon());
                        }
                    }
                }
            }else if(type ==1){
                BabySitting babySitting = babySittingDao.findOne(typeId);
                if(babySitting !=null){
//                    bookingDto.setShareAddress();
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(babySitting.getPhotosPath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    Account owner = babySitting.getOwner();
                    if(owner!=null){
                        UserInfo userInfo = owner.getUserInfo();
                        bookingDto.setUserName(owner.getUserName());
                        if(userInfo!=null){
                            bookingDto.setUserIcon(userInfo.getUserIcon());
                        }
                    }
                }
            }else if(type==2){
                Event event = eventDao.findOne(typeId);
                if(event!=null){
                    bookingDto.setShareAddress(event.getAddress());
                    JavaType javaType = jsonMapper.buildCollectionType(List.class,String.class);
                    List<String> photoPahts = jsonMapper.fromJson(event.getImagePath(), javaType);
                    bookingDto.setShareIcon(photoPahts);
                    bookingDto.setAddressLat(event.getAddressLat());
                    bookingDto.setAddressLon(event.getAddressLon());
                    bookingDto.setWhereToMeetLat(event.getWhereToMeetLat());
                    bookingDto.setWhereToMeetLon(event.getWhereToMeetLon());
                    Account owner = event.getOwner();
                    if(owner!=null){
                        UserInfo userInfo = owner.getUserInfo();
                        bookingDto.setUserName(owner.getUserName());
                        if(userInfo!=null){
                            bookingDto.setUserIcon(userInfo.getUserIcon());
                        }
                    }
                }
            }
            JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
            List<UserInfoDto.ChildrenInfo> whoIsComings = jsonMapper.fromJson(booking.getWhoIsComings(),javaType);
            bookingDto.setWhoIsComings(whoIsComings);
            result.add(bookingDto);
        }
        Page<BookingDto> pageResult = new PageImpl<BookingDto>(result,pageable,data.getTotalElements());
        return pageResult;
    }


    /**
     * 获取已预定列表
     * @param accountDto 当前用户
     * @param shareType 分享类型
     * @param shareTypeId 类型id
     * @return 已预定列表
     */
    public List<UserInfoDto.ChildrenInfo> getAppointedBookingList(AccountDto accountDto, Integer shareType, Long shareTypeId){
        List<Booking> data = bookingDao.findAllByCareTypeAndTypeId(shareType,shareTypeId);
        List<UserInfoDto.ChildrenInfo> result = Lists.newArrayList();
        for(Booking booking : data){
            String whoIsComings = booking.getWhoIsComings();
            JavaType javaType = jsonMapper.buildCollectionType(List.class, UserInfoDto.ChildrenInfo.class);
            List<UserInfoDto.ChildrenInfo> childInfo = jsonMapper.fromJson(whoIsComings, javaType);
            result.addAll(childInfo);
        }
        return result;
    }

    /**
     * 进行confirm操作
     * @param accountDto 当前用户
     * @param bookingId 订单id
     */
    public void acceptBooking(AccountDto accountDto, Long bookingId){
        Booking booking = bookingDao.findOne(bookingId);
        booking.setBookingStatus(1);
    }


    /**
     * 进行reject操作
     * @param accountDto 当前用户
     * @param rejectRequestDto 拒绝请求
     */
    public void rejectBooking(AccountDto accountDto, RejectRequestDto rejectRequestDto){
        Booking booking = bookingDao.findOne(rejectRequestDto.getBookingId());
        booking.setBookingStatus(2);
        booking.setRejectReason(rejectRequestDto.getRejectReason());
    }

}
