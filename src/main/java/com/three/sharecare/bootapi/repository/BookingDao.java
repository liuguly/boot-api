package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingDao extends PagingAndSortingRepository<Booking, Long> {

    /**
     * 根据careType和accountId查询
     *
     * @param careType  类型
     * @param accountId 账户id
     * @return 根据类型查询
     */
    Page<Booking> findByCareTypeAndAccount_Id(Integer careType, Long accountId, Pageable pageable);

    /**
     * 根据accountId查询预定信息
     *
     * @param accountId 账户id
     * @param pageable  分页
     * @return 全部
     */
    Page<Booking> findAllByAccount_Id(Long accountId, Pageable pageable);

    /**
     * 查找past的
     *
     * @param currentDate 当前时间
     * @param pageable    分页
     * @return 分页结果
     */
    Page<Booking> findAllByAccount_IdAndStartDateBefore(Long accountId, Date currentDate, Pageable pageable);


    /**
     * 查找comming的
     *
     * @param currentDate 当前时间
     * @param pageable    分页
     * @return 分页结果
     */
    Page<Booking> findAllByAccount_IdAndStartDateGreaterThanEqual(Long accountId, Date currentDate, Pageable pageable);

    /**
     * 查找即将到来的付款后的booking列表
     * @param accountId 账户id
     * @return
     */
    @Query(value = "SELECT b.* FROM sb_booking b INNER JOIN sb_payment p " +
            "ON p.care_type=b.care_type AND p.share_id=b.type_id AND b.account_id=p.account_id " +
            "WHERE p.account_id=?1 " +
            "AND b.start_date>=sysdate() " +
            "ORDER BY ?#{#pageable} ",
            countQuery = "SELECT COUNT(*) FROM (SELECT b.* FROM sb_booking b INNER JOIN sb_payment p " +
                    "ON p.care_type=b.care_type AND p.share_id=b.type_id AND b.account_id=p.account_id " +
                    "WHERE p.account_id=?1 " +
                    "AND b.start_date>=sysdate()) c1",
            nativeQuery = true)
    Page<Booking> findUpcomingPaymentBookins(Long accountId,Pageable pageable);

    /**
     * 根据分享类型和id查询当前booking信息
     *
     * @param careType 分享类型
     * @param typeId   分享id
     * @return 预定信息
     */
    @Query(value = "select b from Booking as b " +
            "left join fetch b.account as a " +
            "left join fetch a.userInfo as u where b.careType=?1 and b.typeId=?2")
    List<Booking> findAllByCareTypeAndTypeId(Integer careType, Long typeId);


    @Query(value = "select b from Booking as b " +
            "left join fetch b.account as a " +
            "left join fetch a.userInfo as u " +
            "where a.id=?1 and b.careType=?2")
    List<Booking> findAllByAccount_IdAndCareType(Long accountId, Integer careType, Pageable pageable);


    @Query(value = "SELECT b.* FROM sharecare.sb_booking b " +
            "LEFT JOIN sharecare.sb_share_care s " +
            "ON b.type_id=s.id " +
            "WHERE s.owner_id=?1 AND b.care_type=0 " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT (*) FROM (SELECT b.* FROM sharecare.sb_booking b " +
                    "LEFT JOIN sharecare.sb_share_care s " +
                    "ON b.type_id=s.id " +
                    "WHERE s.owner_id=?1 AND b.care_type=0)",
            nativeQuery = true)
    Page<Booking> findByOthersBookingMeSharecare(Long accountId, Pageable pageable);


    @Query(value = "SELECT b.* FROM sharecare.sb_booking b " +
            "LEFT JOIN sharecare.sb_baby_sitting s " +
            "ON b.type_id=s.id " +
            "WHERE s.owner_id=?1 AND b.care_type=1 " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT (*) FROM (SELECT b.* FROM sharecare.sb_booking b " +
                    "LEFT JOIN sharecare.sb_baby_sitting s " +
                    "ON b.type_id=s.id " +
                    "WHERE s.owner_id=?1 AND b.care_type=1)",
            nativeQuery = true)
    Page<Booking> findByOthersBookingMeBabysitting(Long accountId, Pageable pageable);


    @Query(value = "SELECT b.* FROM sharecare.sb_booking b " +
            "LEFT JOIN sharecare.sb_event s " +
            "ON b.type_id=s.id " +
            "WHERE s.owner_id=?1 AND b.care_type=2 " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT (*) FROM (SELECT b.* FROM sharecare.sb_booking b " +
                    "LEFT JOIN sharecare.sb_event s " +
                    "ON b.type_id=s.id " +
                    "WHERE s.owner_id=?1 AND b.care_type=2)",
            nativeQuery = true)
    Page<Booking> findByOthersBookingMeEvent(Long accountId, Pageable pageable);


    @Query(value = "select b from Booking b " +
            "left join fetch b.account a " +
            "left join fetch a.userInfo u " +
            "where b.careType=?1 and b.typeId=?2 and a.id=?3 ")
    Booking findBookingByCareTypeAndTypeIdAndAccount_Id(Integer careType,Long typeId,Long accountId);

}
