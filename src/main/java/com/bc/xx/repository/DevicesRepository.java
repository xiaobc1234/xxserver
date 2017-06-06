package com.bc.xx.repository;

import com.bc.xx.model.Devices;
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
public interface DevicesRepository extends JpaRepository<Devices,Integer>,JpaSpecificationExecutor<Devices> {

    List<Devices> findByGamesAlias(String gamesAlias);

    List<Devices> findByDeviceId(String deviceId);

}
