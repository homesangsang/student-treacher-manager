package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.AuthorityDao;
import cn.edu.qlu.studentteachermanager.dao.TeacherDao;
import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private ExperimentClassesService experimentClassesService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private StudentService studentService;

    private Logger logger = LoggerFactory.getLogger(Teacher.class);

    /**
     * 添加老师
     * @param teacher
     * @return
     */
    public Teacher addTeacher(Teacher teacher) {
        MyUser user = userDao.findByUsername(teacher.getTnumber());
        if (user == null || user.getId() == null) {
            user = new MyUser();
            user.setUsername(teacher.getTnumber());
            user.setIfFirstLogin(true);
            List<Authority> authorities = new ArrayList<>();
            Authority authority = authorityDao.findById(2);
            authorities.add(authority);

            user.setPassword(bCryptPasswordEncoder.encode("666666"));
            user.setAuthorities(authorities);
            userDao.save(user);
            authorityDao.save(authorities);
        }
        return teacherDao.save(teacher);
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


    /**
     * f分页查询老师列表
     * @param pageRequest
     * @return
     */
    public Page<Teacher> findAllTeacher(PageRequest pageRequest) {
        return teacherDao.findAll(pageRequest);
    }

    /**
     * 通过老师id获取老师信息
     * @param teacherId
     * @return
     */
    public Teacher getTeacherById(Integer teacherId) {
        return teacherDao.findById(teacherId);
    }

    /**
     * 老师添加选课信息
     * @param experimentClasses
     * @return
     */
    public void addExpClass(ExperimentClasses experimentClasses, String username) {
        Teacher teacher = teacherDao.findByTnumber(username);

        ExperimentClasses exp = experimentClassesService.findById(experimentClasses.getId());
        if (exp == null || exp.getId() == null) {
            List<Teacher> list = new ArrayList<>();
            list.add(teacher);
            experimentClasses.setTeachers(list);
            if (teacher.getExperimentClasses() != null) {
                teacher.getExperimentClasses().add(experimentClasses);
            } else {
                List<ExperimentClasses> list1 = new ArrayList<>();
                list1.add(experimentClasses);
                teacher.setExperimentClasses(list1);
            }
            experimentClassesService.save(experimentClasses);
            teacherDao.save(teacher);

        } else {
            List<Teacher> ts = exp.getTeachers();
            if (ts != null) {
                exp.getTeachers().add(teacher);
            } else {

                List<Teacher> list = new ArrayList<>();
                list.add(teacher);
                exp.setTeachers(list);
            }
            experimentClassesService.save(exp);
        }

    }

    /**
     * 老师查询公告/通知方法
     * @param pageRequest
     * @param onlyMe
     * @param username
     * @return
     */
    public Page<Announcement> selectAnnouByPage(PageRequest pageRequest, Integer onlyMe, String username) {
        if (onlyMe == 0) {
            return announcementService.findAll(pageRequest);
        }
        if (onlyMe == 1) {
            Teacher teacher = teacherDao.findByTnumber(username);
        }
        return null;
    }

    /**
     * 老师新增公告的方法
     * @param announcement
     * @param username
     * @return
     */
    public Announcement addAnn(Announcement announcement, String username) {
        Teacher teacher = teacherDao.findByTnumber(username);
        announcement.setTeacher(teacher);
        return announcementService.save(announcement);
    }


    public void deleteAnn(String username, Announcement announcement) {
        Teacher teacher = teacherDao.findByTnumber(username);
        Announcement ann = announcementService.findAnnById(announcement.getId());
        teacher.getAnnouncementList().remove(ann);
        announcementService.remove(ann);
        teacherDao.save(teacher);

    }

    /**
     * 通过teacher的工号查找老师信息
     * @param username
     * @return
     */
    public Teacher findTeacherByUsername (String username) {
        return teacherDao.findByTnumber(username);
    }

    /**
     * 根据课程id查看查看对应课程的选课情况
     * @param username
     * @param id
     * @return
     */
    public List<Student> selectExpClassStudent(String username, Integer id) {
        Teacher teacher = teacherDao.findByTnumber(username);
        ExperimentClasses  experimentClasses = experimentClassesService.findById(id);
        return experimentClasses.getStudents();
    }

    /**
     * 删除选课
     * 课程-老师 多对多，课程-学生 多对多
     * 先删除课程与学生的关联，再删除老师与课程的关联，最后删除课程
     * @param username
     * @param classes
     * @return
     */
    public void deleteExpClass(String username, ExperimentClasses classes) {
        Teacher teacher = teacherDao.findByTnumber(username);
        ExperimentClasses experimentClasses = experimentClassesService.findById(classes.getId());
        List<Student> students = experimentClasses.getStudents();
        // 取消和学生的关联
        if (students != null || students.size() == 0) {
            for (Student student : students) {
                student.setExperimentClasses(null);
                experimentClasses.setStudents(null);
                experimentClassesService.save(experimentClasses);
                studentService.save(student);
            }
        }
        // 取消老师和课程的关联
        classes.setTeachers(null);
        List<ExperimentClasses> experimentClassesList = teacher.getExperimentClasses();
        if (experimentClassesList != null) {
            for (ExperimentClasses cls : experimentClassesList) {
                if (cls.getId() == classes.getId()) {
                    experimentClassesList.remove(cls);
                    break;
                }
            }
        }
        experimentClassesService.save(classes);
        teacherDao.save(teacher);
        experimentClassesService.delete(classes);
        // 删除课程
    }

    public void deleteTeacher(Teacher teacher) {
        teacherDao.delete(teacher);
        MyUser user = userDao.findByUsername(teacher.getTnumber());
        if (user != null ) {
            userDao.delete(user);
        }
    }
}
