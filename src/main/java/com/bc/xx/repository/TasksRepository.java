package com.bc.xx.repository;

import com.bc.xx.model.Games;
import com.bc.xx.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * GamesRepository
 *
 * @author xiaobc
 * @date 17/6/3
 */
@Repository
public interface TasksRepository extends JpaRepository<Tasks,Integer>,JpaSpecificationExecutor<Tasks> {
}