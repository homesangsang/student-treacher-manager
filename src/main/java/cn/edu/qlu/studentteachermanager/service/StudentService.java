package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.StudentDao;
import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import cn.edu.qlu.studentteachermanager.entity.MyUser;
import cn.edu.qlu.studentteachermanager.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ExperimentClassesService experimentClassesService;
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
        user.setIfFirstLogin(true);
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

    /**
     * 选课
     * @param expClassId
     * @param username
     */
    public void selectExpClassById(Integer expClassId, String username) {
        Student student = studentDao.findBySnumber(username); // 获取学生对象
        ExperimentClasses exp = experimentClassesService.findById(expClassId); //获取课程对象
        exp.setSum(exp.getSum() - 1);
        experimentClassesService.save(exp);
        student.getExperimentClasses().add(exp);
        studentDao.save(student);
    }

    /**
     * 查看已选课程列表
     * @param username
     * @return
     */
    public List<ExperimentClasses> findSelectedExp(String username) {
        Student student = studentDao.findBySnumber(username);
        return student.getExperimentClasses();
    }
}
