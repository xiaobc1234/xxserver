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
@Table(name="tasks")
public class Tasks implements Serializable {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "INT")
    private Integer id;

    //任务名称
    private String taskName;
    //任务方法
    private String methodName;
    //删除标识符
    private Integer delFlag;
    //应用id
    private Integer gamesId;
    //应用名称
    private String gamesName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getGamesId() {
        return gamesId;
    }

    public void setGamesId(Integer gamesId) {
        this.gamesId = gamesId;
    }

    public String getGamesName() {
        return gamesName;
    }

    public void setGamesName(String gamesName) {
        this.gamesName = gamesName;
    }
}
