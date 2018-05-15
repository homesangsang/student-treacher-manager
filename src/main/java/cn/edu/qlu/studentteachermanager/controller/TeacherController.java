package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.entity.Announcement;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import cn.edu.qlu.studentteachermanager.service.AnnouncementService;
import cn.edu.qlu.studentteachermanager.service.ExperimentClassesService;
import cn.edu.qlu.studentteachermanager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping("/selectExpClass")
    public Page<ExperimentClasses> selectExpClassByPage(
            Authentication authentication,
            Integer id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return experimentClassesService.findAll(new PageRequest(page, size));
    }


    /**
     * 查看通知公告
     * @param page
     * @param size
     * @param onlyMe
     * @return
     */
    @RequestMapping("/selectAnnouncement")
    public Page<Announcement> selectAnnouByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "onlyme", defaultValue = "0") Integer onlyMe, // 1. 只看自己 0. 查看全部
            Authentication authentication
    ) {
        return teacherService.selectAnnouByPage(new PageRequest(page, size), onlyMe, (String)authentication.getPrincipal());
    }

    /**
     * 发布公告
     * @param announcement
     * @param authentication
     * @return
     */
    @PostMapping("/addAnn")
    public Announcement addAnn(@RequestBody Announcement announcement, Authentication authentication) {
        return teacherService.addAnn(announcement, (String)authentication.getPrincipal());
    }



}
