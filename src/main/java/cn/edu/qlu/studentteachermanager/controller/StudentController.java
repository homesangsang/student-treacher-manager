package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.entity.Announcement;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    /**
     * 查看公告
     * @param authentication
     * @param id
     * @param size
     * @return
     */
    @RequestMapping("/selectAnno")
    public Page<Announcement> selectAnnoByPage(
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "0") Integer id,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return  null;
    }


    /**
     * 根据通知ID查看通知详情
     * @param announcementId
     * @return
     */
    @RequestMapping("/findAnnoInfo")
    public Announcement findAnnoInfoById(Integer announcementId) {
        return  null;
    }

    /**
     * 分页显示选课列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/selectExpClass")
    public Page<ExperimentClasses> selectExpClassByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "0") Integer size
    ) {
        return  null;
    }

    /**
     * 根据课程id查看课程详细信息
     * @param id
     * @return
     */
    @RequestMapping("/findExpClassById")
    public ExperimentClasses findExpClassById(Integer id) {
        return null;
    }

    /**
     * 选课动作
     * @param id
     * @return
     */
    @RequestMapping("/selectExpClassById")
    public Map<Object, Object> selectExpClassById(Integer id) {
        return null;
    }
}
