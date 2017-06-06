package com.bc.xx.repository;

import com.bc.xx.model.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GamesRepository
 *
 * @author xiaobc
 * @date 17/6/3
 */
@Repository
public interface GamesRepository extends JpaRepository<Games,Integer>,JpaSpecificationExecutor<Games> {

    List<Games> findByAlias(String alias);

}
