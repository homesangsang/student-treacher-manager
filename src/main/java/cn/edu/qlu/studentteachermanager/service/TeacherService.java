package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.TeacherDao;
import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.MyUser;
import cn.edu.qlu.studentteachermanager.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 添加老师
     * @param teacher
     * @return
     */
    public Teacher addTeacher(Teacher teacher) {
        MyUser user = new MyUser();
        user.setUsername(teacher.getTnumber());
        user.setPassword(bCryptPasswordEncoder.encode("666666"));
        return teacherDao.save(teacher);
    }


    /**
     * f分页查询老师列表
     * @param pageRequest
     * @return
     */
    public Page<Teacher> findAllTeacher(PageRequest pageRequest) {
        return teacherDao.findAll(pageRequest);
    }

    public Teacher getTeacherById(Integer teacherId) {
        return teacherDao.findById(teacherId);
    }


}
