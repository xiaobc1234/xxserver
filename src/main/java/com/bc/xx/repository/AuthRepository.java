package com.bc.xx.repository;

import com.bc.xx.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * AuthRepository
 *
 * @author xiaobc
 * @date 17/6/9
 */
@Repository
public interface AuthRepository extends JpaRepository<Auth,Integer>,JpaSpecificationExecutor<Auth> {

    List<Auth> findByUsernameAndPassword(String username, String password);

}