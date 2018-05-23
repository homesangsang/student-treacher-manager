package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.AdminDao;
import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.MyUser;
import cn.edu.qlu.studentteachermanager.entity.PasswordContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 重置密码
     * @param number
     * @param passes
     * @return
     */
    public String updatePassword(String number, PasswordContent passes) {
        MyUser user = userDao.findByUsername(number);
        String oldBcPass = user.getPassword();
        boolean ifOldPass = bCryptPasswordEncoder.matches(passes.getOldPassword(), oldBcPass);
        if (ifOldPass) { // 如果旧密码正确，ifOldPass == true
            user.setPassword(bCryptPasswordEncoder.encode(passes.getNewPassword())); // 更新密码
            userDao.save(user);
            return "密码更新成功";
        }  else {
            return "旧密码不正确";
        }

    }
}
