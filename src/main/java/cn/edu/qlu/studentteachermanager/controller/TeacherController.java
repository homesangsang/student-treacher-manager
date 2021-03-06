package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.entity.Announcement;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import cn.edu.qlu.studentteachermanager.entity.PasswordContent;
import cn.edu.qlu.studentteachermanager.entity.Student;
import cn.edu.qlu.studentteachermanager.message.ResultMessage;
import cn.edu.qlu.studentteachermanager.service.AnnouncementService;
import cn.edu.qlu.studentteachermanager.service.ExperimentClassesService;
import cn.edu.qlu.studentteachermanager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Secured("ROLE_TEACHER")
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ExperimentClassesService experimentClassesService;

    /**
     * 老师添加新的实验课信息
     * @param experimentClasses
     * @return
     */
    @RequestMapping("/addExpClass")
    public String addExpClass(@RequestBody ExperimentClasses experimentClasses, Authentication authentication) {
        String username = (String)authentication.getPrincipal();
        if (experimentClasses != null) {
            teacherService.addExpClass(experimentClasses, username);
            return "success";
        }
        return "false";
    }

    /**
     *
     * @param authentication
     * @param id 课程id，根据id查询信息，如果id为空则查询全部课程信息
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/selectExpClass")
    public ResultMessage selectExpClassByPage(
            Authentication authentication,
            Integer id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (page > 0) {
            page--;
        }
        String username = (String)authentication.getPrincipal();
        Page<ExperimentClasses> pages  = experimentClassesService.findAll(new PageRequest(page, size));
        List<ExperimentClasses> experimentClassesList = teacherService.findTeacherByUsername(username).getExperimentClasses();
        return new ResultMessage(200, experimentClassesList, Long.parseLong(String.valueOf(experimentClassesList.size())), "success");
    }

    /**
     * 查看不同课程的选课详情
     * @param authentication
     * @param id
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/selectExpClassStudent")
    public ResultMessage selectExpClassStudent(
            Authentication authentication,
            Integer id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        String username = (String)authentication.getPrincipal();
        List<Student> students = teacherService.selectExpClassStudent(username, id);
        ResultMessage resultMessage  = new ResultMessage(200, students, Long.parseLong(String.valueOf(students.size())), "success");
        return resultMessage;
    }

    /**
     * 根据ID删除选课信息
     * @param authentication
     * @param experimentClasses
     * @return
     */
    @RequestMapping("/deleteExpClassById")
    public String deleteExpClassById(
            Authentication authentication,
            @RequestBody ExperimentClasses experimentClasses
    ) {
        String username = (String)authentication.getPrincipal();
        teacherService.deleteExpClass(username, experimentClasses);
        return "删除id："  + "成功";
    }


    /**
     * 查看通知公告
     * @param page
     * @param size
     * @param onlyMe
     * @return
     */
    @RequestMapping("/selectAnnouncement")
    public ResultMessage selectAnnouByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "onlyme", defaultValue = "0") Integer onlyMe, // 1. 只看自己 0. 查看全部
            Authentication authentication
    ) {
        if (page >0 ) {
            page--;
        }
        Page<Announcement> pages = teacherService.selectAnnouByPage(new PageRequest(page, size), onlyMe, (String)authentication.getPrincipal());
        return new ResultMessage(200, pages.getContent(), pages.getTotalElements(), "success");
    }

    /**
     * 发布公告
     * @param announcement
     * @param authentication
     * @return
     */
    @PostMapping(value = "/addAnn", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Announcement addAnn(@RequestBody Announcement announcement, Authentication authentication) {
        return teacherService.addAnn(announcement, (String)authentication.getPrincipal());
    }

    /**
     * 删除发布公告
     * @param authentication
     * @param announcement
     * @return
     */
    @PostMapping(value = "/delAnn")
    public String deleteAnn(Authentication authentication, @RequestBody Announcement announcement) {
        String username = (String)authentication.getPrincipal();
        teacherService.deleteAnn(username, announcement);
        return "success";
    }

    /**
     *
     * @param passwordContent
     * @param authentication
     * @return
     */
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestBody PasswordContent passwordContent, Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        return teacherService.updatePassword(username, passwordContent);
    }
}
