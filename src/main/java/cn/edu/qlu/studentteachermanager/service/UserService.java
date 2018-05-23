package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.JwtUserFactory;
import cn.edu.qlu.studentteachermanager.entity.MyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService{
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserDao userDao;

    public MyUser login(MyUser myUser) {
        return userDao.findByUsername(myUser.getUsername());
    }

    public MyUser save(MyUser myUser) {
        return userDao.save(myUser);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        MyUser applicationUser = userDao.findByUsername(s);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(s);
        }
        //return new User(applicationUser.getUsername(), applicationUser.getPassword(), Collections.emptyList());

        logger.info("原密码：666666" + bCryptPasswordEncoder.matches("666666", applicationUser.getPassword()));
        logger.info("原密码：123456" + bCryptPasswordEncoder.matches("123456", applicationUser.getPassword()));
        logger.info("返回UserDetails" + applicationUser.toString());
        return JwtUserFactory.create(applicationUser);
    }
}
