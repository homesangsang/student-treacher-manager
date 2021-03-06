package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.AuthorityDao;
import cn.edu.qlu.studentteachermanager.dao.StudentDao;
import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private AuthorityDao authorityDao;

    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    /**
     * 添加学生的方法
     * @param student
     * @return
     */
    public Student addStudent(Student student) {
        // 用户
        MyUser user = userDao.findByUsername(student.getSnumber());
        if (user == null || user.getId() == null) {
            user = new MyUser();
            user.setUsername(student.getSnumber());
            user.setIfFirstLogin(true);
            List<Authority> authorities = new ArrayList<>();
            Authority authority = authorityDao.findById(3);
            authorities.add(authority);

            user.setPassword(bCryptPasswordEncoder.encode("666666"));
            user.setAuthorities(authorities);
            userDao.save(user);
            authorityDao.save(authorities);
        }
        // 权限
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
    public boolean selectExpClassById(Integer expClassId, String username) {
        Student student = studentDao.findBySnumber(username); // 获取学生对象
        ExperimentClasses exp = experimentClassesService.findById(expClassId); //获取课程对象
        if (exp.getSum() <= 0) {
            return false;
        }
        exp.setSum(exp.getSum() - 1);
        if (exp.getStudents() != null) {
            List<Student> stus = exp.getStudents();
            for (Student stu : stus) {
                if (stu.getSnumber().equals(student.getSnumber())) {
                    return false;
                }
            }
            exp.getStudents().add(student);
        } else {
            List<Student> list = new ArrayList<>();
            list.add(student);
            exp.setStudents(list);
        }
        if (student.getExperimentClasses() != null) {
            student.getExperimentClasses().add(exp);
        } else {
            List<ExperimentClasses> list = new ArrayList<>();
            list.add(exp);
            student.setExperimentClasses(list);
        }
        experimentClassesService.save(exp);
        studentDao.save(student);
        return true;
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

    /**
     * 退选课程
     * @param snumber
     * @param expId
     */
    public void deleteExpBySnumber(String snumber, Integer expId) {
        Student student = studentDao.findBySnumber(snumber);
        ExperimentClasses experimentClasses = experimentClassesService.findById(expId);
        List<ExperimentClasses> exps = student.getExperimentClasses();
        for (ExperimentClasses exp:exps) {
            if (exp.getId() == experimentClasses.getId()) {
                student.getExperimentClasses().remove(exp);
                experimentClasses.getStudents().remove(student);
                experimentClassesService.save(experimentClasses);
                studentDao.save(student);
                break;
            }
        }

    }

    /**
     * 删除学生
     * @param student
     */
    public void deleteStudentById(Student student) {
        studentDao.delete(student);
    }

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

    public void save(Student student) {
        studentDao.save(student);
    }
}
