package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.StudentDao;
import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.MyUser;
import cn.edu.qlu.studentteachermanager.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 添加学生的方法
     * @param student
     * @return
     */
    public Student addStudent(Student student) {
        MyUser user = new MyUser();
        user.setUsername(student.getSnumber());
        user.setPassword(bCryptPasswordEncoder.encode("666666"));
        userDao.save(user);
        return studentDao.save(student);
    }

    /**
     * 分页查询学生列表
     * @param pageRequest
     * @return
     */
    public Page<Student> findAllStudent(PageRequest pageRequest) {
        return studentDao.findAll(pageRequest);
    }

    /**
     * 根据ID查询学生
     * @param studentId
     * @return
     */
    public Student getStudentById(Integer studentId) {
        return studentDao.findById(studentId);
    }
}
