package com.three.sharecare.bootapi.repository;


import com.three.sharecare.bootapi.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 基于Spring Data JPA的Dao接口, 自动根据接口生成实现.
 * 
 * CrudRepository默认有针对实体对象的CRUD方法.
 * 
 * Spring Data JPA 还会解释新增方法名生成新方法的实现.
 */
@Repository
public interface AccountDao extends CrudRepository<Account, Long> {

	Account findByEmail(String email);

	Account findByToken(String token);

}
