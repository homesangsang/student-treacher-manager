package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.entity.Announcement;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Secured("ROLE_TEACHER")
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 老师添加新的实验课信息
     * @param experimentClasses
     * @return
     */
    @RequestMapping("/addExpClass")
    public ExperimentClasses addExpClass(ExperimentClasses experimentClasses) {
        if (experimentClasses != null) {

        }
        return null;
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
        return null;
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
            @RequestParam(value = "onlyme", defaultValue = "0") Integer onlyMe // 1. 只看自己 0. 查看全部
    ) {
        return null;
    }


}
