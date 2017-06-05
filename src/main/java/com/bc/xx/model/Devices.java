package com.bc.xx.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Games
 *
 * @author xiaobc
 * @date 17/6/3
 */
@Entity
@Table(name="devices")
public class Devices implements Serializable {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "INT")
    private Integer id;

    //设备id
    private String deviceId;
    //设备别名
    private String alias;
    //应用别名 唯一标示
    private String gamesAlias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGamesAlias() {
        return gamesAlias;
    }

    public void setGamesAlias(String gamesAlias) {
        this.gamesAlias = gamesAlias;
    }
}
