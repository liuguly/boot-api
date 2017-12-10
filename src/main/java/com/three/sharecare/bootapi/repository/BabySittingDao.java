package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.BabySitting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabySittingDao extends PagingAndSortingRepository<BabySitting, Long> {

    /**
     * 获取喜欢的sharecare列表
     * @param accountId 当前用户id
     * @param fType 喜欢的类型  0-> sharecare  1-> babysitting  2->event
     * @param pageable 分页
     * @return 分页结果
     */
    @Query(value = "SELECT s.* FROM sharecare.sb_baby_sitting s WHERE s.id IN (" +
            "SELECT sf.f_type_id FROM sb_favorite sf INNER JOIN (" +
            "SELECT f.f_type_id,f.f_type,f.favorite_account_id,max(f.create_time) f_create_time " +
            "FROM sharecare.sb_favorite f " +
            "WHERE f.f_type=?2 AND f.favorite_account_id=?1 " +
            "GROUP BY f.f_type_id,f.f_type,f.favorite_account_id) isb " +
            "WHERE sf.create_time=isb.f_create_time AND sf.status!=0) " +
            "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT (*) FROM " +
                    "(" +
                    "SELECT s.* FROM sharecare.sb_baby_sitting s WHERE s.id IN (" +
                    "SELECT sf.f_type_id FROM sb_favorite sf INNER JOIN (" +
                    "SELECT f.f_type_id,f.f_type,f.favorite_account_id,max(f.create_time) f_create_time " +
                    "FROM sharecare.sb_favorite f " +
                    "WHERE f.f_type=?2 AND f.favorite_account_id=?1 " +
                    "GROUP BY f.f_type_id,f.f_type,f.favorite_account_id) isb " +
                    "WHERE sf.create_time=isb.f_create_time AND sf.status!=0) " +
                    ")",
            nativeQuery = true)
    Page<BabySitting> findFavoriteBabySitting(Long accountId, Integer fType, Pageable pageable);

    /**
     * 获取自己发布的babysitting
     * @param accountId current account id
     * @param pageable 分页
     * @return 分页结果
     */
    Page<BabySitting> findAllByOwnerId(Long accountId, Pageable pageable);
}
