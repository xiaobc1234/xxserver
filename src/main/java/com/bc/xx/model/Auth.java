package com.bc.xx.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * auth
 *
 * @author xiaobc
 * @date 17/6/9
 */
@Entity
@Table(name = "tb_auth")
public class Auth {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "INT")
    private int id;

    private String username;    //账号
    private String password;       //密码


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
