package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.entity.MyUser;
import cn.edu.qlu.studentteachermanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    public String signUp(@RequestBody MyUser myUser) {
        myUser.setPassword(bCryptPasswordEncoder.encode("666666"));
        userService.save(myUser);
        logger.info(myUser.toString());
        return "sign up successful!";

    }


    @GetMapping(value = "/hello")
    public Map<Object, Object> hello(Authentication authentication) {
        String user = (String)authentication.getPrincipal(); // 获取登录用户的用户名
        Map<Object, Object> map = new HashMap<>();
        map.put("username", user);
        map.put("password", "word");
        return map;
    }

}
