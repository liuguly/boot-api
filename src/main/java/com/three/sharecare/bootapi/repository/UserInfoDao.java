package com.three.sharecare.bootapi.repository;

import com.three.sharecare.bootapi.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends CrudRepository<UserInfo,Long> {

}
