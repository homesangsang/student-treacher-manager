package cn.edu.qlu.studentteachermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 权限表
 * User: 桑智勇(homesangsang)
 * Date: 2018-05-14
 * Time: 10:19
 */
@Entity
public class Authority {
    @Id
    @GeneratedValue
    private Integer id;

    private String authorityName; // 权限名称

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    private List<MyUser> myUsers; // 查询权限对应的用户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", authorityName='" + authorityName + '\'' +
                '}';
    }

    public List<MyUser> getMyUsers() {
        return myUsers;
    }

    @JsonBackReference
    public void setMyUsers(List<MyUser> myUsers) {
        this.myUsers = myUsers;
    }
}
