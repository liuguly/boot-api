package com.three.sharecare.bootapi.service;

import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.domain.Account;
import com.three.sharecare.bootapi.domain.CreditCard;
import com.three.sharecare.bootapi.dto.AccountDto;
import com.three.sharecare.bootapi.dto.CreditCardDto;
import com.three.sharecare.bootapi.repository.AccountDao;
import com.three.sharecare.bootapi.repository.CreditCardDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CreditCardService {

    @Autowired
    private CreditCardDao creditCardDao;

    @Autowired
    private AccountDao accountDao;

    public CreditCard saveCreditCard(AccountDto accoutDto, CreditCardDto creditCardDto){
        Long id = accoutDto.getId();
        CreditCard creditCard = new CreditCard();
        BeanUtils.copyProperties(creditCardDto,creditCard);
        creditCard.setCreateTime(new Date());
        creditCard.setUpdateTime(new Date());
        Account account = accountDao.findOne(id);
        creditCard.setAccount(account);
        creditCard = creditCardDao.save(creditCard);
        return creditCard;
    }


    public List<CreditCardDto> findCreditCardInfo(AccountDto accountDto){
        Long accountId = accountDto.getId();
        List<CreditCard> data = creditCardDao.findCreditCardsByAccount_Id(accountId);
        List<CreditCardDto> result = Lists.newArrayList();
        for(CreditCard creditCard : data){
            CreditCardDto creditCardDto = new CreditCardDto();
            BeanUtils.copyProperties(creditCard,creditCardDto);
            result.add(creditCardDto);
        }
        return result;
    }


    public Integer deleteCreditCard(AccountDto accountDto,Long id){
        Long accountId = accountDto.getId();
        int result = creditCardDao.deleteCreditCardBy(accountId,id);
        return result;
    }


}
