package com.bc.xx.repository;

import com.bc.xx.model.TasksQueue;
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
public interface TasksQueueRepository extends JpaRepository<TasksQueue,Integer>,JpaSpecificationExecutor<TasksQueue> {

    List<TasksQueue> findByDeviceIdOrderBySortAsc(String deviceId);

}
