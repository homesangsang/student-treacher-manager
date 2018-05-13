package cn.edu.qlu.studentteachermanager.dao;

import cn.edu.qlu.studentteachermanager.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<MyUser, Integer>{
    MyUser findByUsername(String username);


}
