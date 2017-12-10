package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.Favorite;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FavoriteDao extends PagingAndSortingRepository<Favorite, Long> {

    /**
     * 获取喜欢的类型列表
     * 分组查询并获取每组最新的数据
     * @param accountId 当前用户id
     * @param fType 喜欢的类型  0-> sharecare  1-> babysitting  2->event
     * @return 结果
     */
    @Query(value = "SELECT f.* FROM sharecare.sb_favorite f " +
            "INNER JOIN (SELECT fi.*,MAX(fi.create_time) AS fi_create_time " +
            "FROM sharecare.sb_favorite fi " +
            "GROUP BY fi.f_type,fi.f_type_id,fi.favorite_account_id) AS ifi " +
            "WHERE f.create_time=ifi.fi_create_time " +
            "AND f.f_type_id=ifi.f_type_id " +
            "AND f.f_type=ifi.f_type " +
            "AND f.favorite_account_id=ifi.favorite_account_id " +
            "AND f.favorite_account_id=?1 " +
            "AND f.f_type=?2 " +
            "ORDER BY f.create_time DESC ",
           nativeQuery = true)
    List<Favorite> findAllByAccount_IdAndFType(Long accountId, Integer fType);

    @Query(value = "select f.id,f.fType,f.account,f.fTypeId,f.status,f.createTime,f.updateTime " +
            "from Favorite f where f.fType=?1")
    List<Favorite> findById(Integer fType);

    /**
     * @param accountId 账户id
     * @param fType 0-> sharecare 1->babysitting 2->event
     * @param fTypeId 三种类型的主键
     * @return 喜欢的实体
     */
    Favorite findByAccount_IdAndFTypeAndFTypeId(Long accountId, Integer fType,Long fTypeId);


    @Modifying
    @Query(value = "update Favorite set status=?1,updateTime=?2 where id=?3")
    void updateStatusAndUpdateTime(Integer status,Date updateTime,Long id);
}
