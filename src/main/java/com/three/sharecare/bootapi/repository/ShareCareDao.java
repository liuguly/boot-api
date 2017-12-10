package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.ShareCare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareCareDao  extends PagingAndSortingRepository<ShareCare, Long> {


//    @Query(value = "(SELECT id," +
//            "photo_path," +
//            "address, " +
//            "available_time," +
//            "children_num," +
//            "photo_per_day," +
//            "update_time," +
//            "money_per_day," +
//            "headline," +
//            "share_care_content," +
//            "create_time," +
//            "owner_id," +
//            "share_type " +
//            "FROM sb_share_care) " +
//            "UNION " +
//            "(SELECT bs.id," +
//            "bs.photo_path as photosPath, " +
//            "'address', " +
//            "'available_time'," +
//            "'children_num'," +
//            "'photo_per_day'," +
//            "'update_time'," +
//            "bs.charge_per_hour as moneyPerDay, " +
//            "bs.head_line as headline," +
//            "bs.about_me as shareCareContent," +
//            "bs.create_time," +
//            "bs.owner_id," +
//            "bs.share_type " +
//            "FROM sb_baby_sitting bs) " +
//            "UNION " +
//            "(SELECT ev.id," +
//            "ev.image_path as photosPath, " +
//            "'address', " +
//            "'available_time'," +
//            "'children_num'," +
//            "'photo_per_day'," +
//            "'update_time'," +
//            "ev.concession as moneyPerDay, " +
//            "ev.listing_headline as headline, " +
//            "ev.event_description as shareCareContent," +
//            "ev.create_time," +
//            "ev.owner_id," +
//            "ev.share_type " +
//            "FROM sb_event ev) " +
//            "ORDER BY ?#{#pageable} ",
//            countQuery = "SELECT COUNT(share.id) FROM (" +
//                    "(SELECT sc.id," +
//                    "sc.photo_path as photosPath," +
//                    "sc.money_per_day as moneyPerDay," +
//                    "sc.headline as headline," +
//                    "sc.share_care_content as shareCareContent," +
//                    "sc.create_time " +
//                    "FROM sb_share_care sc) " +
//                    "UNION " +
//                    "(SELECT bs.id," +
//                    "bs.photo_path as photosPath, " +
//                    "bs.charge_per_hour as moneyPerDay, " +
//                    "bs.head_line as headline," +
//                    "bs.about_me as shareCareContent," +
//                    "bs.create_time  " +
//                    "FROM sb_baby_sitting bs) " +
//                    "UNION " +
//                    "(SELECT ev.id," +
//                    "ev.image_path as photosPath, " +
//                    "ev.concession as moneyPerDay, " +
//                    "ev.listing_headline as headline, " +
//                    "ev.event_description as shareCareContent," +
//                    "ev.create_time " +
//                    "FROM sb_event ev) " +
//                    ") as share",
//            nativeQuery = true)
//    Page<ShareCare> findAllShare(Pageable pageable);


    /**
     * 获取喜欢的sharecare列表
     * @param accountId 当前用户id
     * @param fType 喜欢的类型  0-> sharecare  1-> babysitting  2->event
     * @param pageable 分页
     * @return 分页结果
     */
    @Query(value = "SELECT s.* FROM sharecare.sb_share_care s WHERE s.id IN (" +
            "SELECT sf.f_type_id FROM sb_favorite sf INNER JOIN (" +
            "SELECT f.f_type_id,f.f_type,f.favorite_account_id,max(f.create_time) f_create_time " +
            "FROM sharecare.sb_favorite f " +
            "WHERE f.f_type=?2 AND f.favorite_account_id=?1 " +
            "GROUP BY f.f_type_id,f.f_type,f.favorite_account_id) isb " +
            "WHERE sf.create_time=isb.f_create_time AND sf.status!=0) " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT (*) FROM " +
                    "(" +
                    "SELECT s.* FROM sharecare.sb_share_care s WHERE s.id IN (" +
                    "SELECT sf.f_type_id FROM sb_favorite sf INNER JOIN (" +
                    "SELECT f.f_type_id,f.f_type,f.favorite_account_id,max(f.create_time) f_create_time " +
                    "FROM sharecare.sb_favorite f " +
                    "WHERE f.f_type=?2 AND f.favorite_account_id=?1 " +
                    "GROUP BY f.f_type_id,f.f_type,f.favorite_account_id) isb " +
                    "WHERE sf.create_time=isb.f_create_time AND sf.status!=0) " +
                    ")",
            nativeQuery = true)
    Page<ShareCare> findFavoriteShareCare(Long accountId, Integer fType, Pageable pageable);

    @Query(value = "select s from ShareCare s " +
            "left join fetch s.reviewList ")
    List<ShareCare> findAllWithReviews(Pageable pageable);

    /**
     * 获取自己发布的sharecare
     * @param accountId 当前账户id
     * @param pageable 分页
     * @return 分页结果
     */
    Page<ShareCare> findAllByOwnerId(Long accountId, Pageable pageable);

    @Query(value = "select s from ShareCare s " +
            "left join fetch s.owner a " +
            "left join fetch a.userInfo " +
            "left join fetch s.reviewList " +
            "where s.id=?1 and a.id=?2 ")
    ShareCare findShareCareByIdAndOwnerId(Long shareCareId, Long accountId);


}
