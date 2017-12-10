package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDao extends PagingAndSortingRepository<Review,Long>{

//    @Query(value = "select r from Review r " +
//            "left join fetch r.account a " +
//            "left join fetch a.userInfo " +
//            "where r.reviewType=?1 and r.shareCareId=?2")
    Page<Review> findReviewsByReviewTypeAndShareCareId(Integer revieType, Long shareCareId, Pageable pageable);

//    @Query(value = "select r from Review r " +
//            "left join fetch r.account a " +
//            "left join fetch a.userInfo  " +
//            "where r.reviewType=?1 and r.babySittingId=?2")
    Page<Review> findReviewsByReviewTypeAndBabySittingId(Integer revieType,Long babySittingId, Pageable pageable);

//    @Query(value = "select r from Review r " +
//            "left join fetch r.account a " +
//            "left join fetch a.userInfo  " +
//            "where r.reviewType=?1 and r.eventId=?2")
    Page<Review> findReviewsByReviewTypeAndEventId(Integer revieType,Long eventId, Pageable pageable);

    Page<Review> findReviewsByAccount_IdAndReviewType(Long accountId,Integer reviewType, Pageable pageable);

    @Query(value = "SELECT * FROM sb_review r " +
            "LEFT JOIN sharecare.sb_share_care s " +
            "ON r.share_care_id=s.id " +
            "WHERE s.owner_id=?1 " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT(*) FROM " +
                    "(SELECT * FROM sb_review r " +
                    "LEFT JOIN sharecare.sb_share_care s " +
                    "ON r.share_care_id=s.id " +
                    "WHERE s.owner_id=?1) c",
            nativeQuery = true)
    Page<Review> findMeShareCareReviews(Long accountId,Pageable pageable);


    @Query(value = "SELECT * FROM sb_review r " +
            "LEFT JOIN sharecare.sb_baby_sitting s " +
            "ON r.baby_sitting_id=s.id " +
            "WHERE s.owner_id=?1 " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT(*) FROM " +
                    "(SELECT * FROM sb_review r " +
                    "LEFT JOIN sharecare.sb_baby_sitting s " +
                    "ON r.baby_sitting_id=s.id " +
                    "WHERE s.owner_id=?1) c",
            nativeQuery = true)
    Page<Review> findMeBabysittingReviews(Long accountId,Pageable pageable);


    @Query(value = "SELECT * FROM sb_review r " +
            "LEFT JOIN sharecare.sb_event s " +
            "ON r.event_id=s.id " +
            "WHERE s.owner_id=?1 " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT(*) FROM " +
                    "(SELECT * FROM sb_review r " +
                    "LEFT JOIN sharecare.sb_event s " +
                    "ON r.event_id=s.id " +
                    "WHERE s.owner_id=?1) c",
            nativeQuery = true)
    Page<Review> findMeEventReviewsByMe(Long accountId,Pageable pageable);


}
