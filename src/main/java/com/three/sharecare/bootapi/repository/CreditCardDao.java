package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.CreditCard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDao extends PagingAndSortingRepository<CreditCard,Long> {


    List<CreditCard> findCreditCardsByAccount_Id(Long accountId);

    @Modifying
    @Query(value = "delete from CreditCard c where c.account.id=?1 and c.id=?2")
    Integer deleteCreditCardBy(Long accountId, Long cardId);

}
