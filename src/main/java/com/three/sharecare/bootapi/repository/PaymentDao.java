package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends PagingAndSortingRepository<Payment,Long> {

    @Query(value = "select p from Payment p " +
            "left join fetch p.account a " +
            "left join fetch a.userInfo " +
            "where p.shareId=?1 and p.careType=?2 and a.id=?3 ")
    Payment findFirstByShareIdAndCareTypeAndAccount_IdOrderByCreateTimeDesc(Long shareCareId,Integer careType,Long accountId);
}
