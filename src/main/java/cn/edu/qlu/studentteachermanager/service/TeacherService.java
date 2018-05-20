package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.TeacherDao;
import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.Announcement;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import cn.edu.qlu.studentteachermanager.entity.MyUser;
import cn.edu.qlu.studentteachermanager.entity.Teacher;
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

    /**
     * 添加老师
     * @param teacher
     * @return
     */
    public Teacher addTeacher(Teacher teacher) {
        MyUser user = new MyUser();
        user.setUsername(teacher.getTnumber());
        user.setIfFirstLogin(true);
        user.setPassword(bCryptPasswordEncoder.encode("666666"));
        userDao.save(user);
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
    public ExperimentClasses addExpClass(ExperimentClasses experimentClasses, String username) {
        Teacher teacher = teacherDao.findByTnumber(username);
//        experimentClasses.getTeachers().add(teacher);
        List<Teacher> list = new ArrayList<>();
        list.add(teacher);
        experimentClasses.setTeachers(list);
        return experimentClassesService.save(experimentClasses);
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
        teacherDao.save(teacher);
        announcementService.remove(ann);
    }
}
