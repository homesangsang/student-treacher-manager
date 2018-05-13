package cn.edu.qlu.studentteachermanager.entity;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户表
 */
@Entity
public class MyUser {
    @Id
    @GeneratedValue
    private Integer id;

    private String username; // 用户名

    private String password; // 密码

    private Integer identity; // 用户类别 1. 管理员  2. 老师  3. 学生

    private Boolean ifFirstLogin; // 是否是第一次登录


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                ", ifFirstLogin=" + ifFirstLogin +
                '}';
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public Boolean getIfFirstLogin() {
        return ifFirstLogin;
    }

    public void setIfFirstLogin(Boolean ifFirstLogin) {
        this.ifFirstLogin = ifFirstLogin;
    }
}
