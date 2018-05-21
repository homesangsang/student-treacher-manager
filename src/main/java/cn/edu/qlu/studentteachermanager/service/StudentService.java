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
    /**
     * 添加学生的方法
     * @param student
     * @return
     */
    public Student addStudent(Student student) {
        MyUser user = userDao.findByUsername(student.getSnumber());
        if (user == null || user.getId() == null) {
            user = new MyUser();
            user.setUsername(student.getSnumber());
            user.setIfFirstLogin(true);
            user.setPassword(bCryptPasswordEncoder.encode("666666"));
            userDao.save(user);
        }
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

    public void deleteStudentById(Student student) {
        studentDao.delete(student);
    }
}
