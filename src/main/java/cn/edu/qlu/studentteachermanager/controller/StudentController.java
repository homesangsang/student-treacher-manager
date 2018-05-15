package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.entity.Announcement;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import cn.edu.qlu.studentteachermanager.service.AnnouncementService;
import cn.edu.qlu.studentteachermanager.service.ExperimentClassesService;
import javassist.compiler.ast.StringL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Secured("ROLE_STUDENT")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ExperimentClassesService experimentClassesService;

    /**
     * 查看公告
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/selectAnno")
    public Page<Announcement> selectAnnoByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return announcementService.findAll(new PageRequest(page, size));
    }


    /**
     * 根据通知ID查看通知详情
     * @param announcementId
     * @return
     */
    @RequestMapping("/findAnnoInfo")
    public Announcement findAnnoInfoById(Integer announcementId) {
        return announcementService.findAnnById(announcementId);
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
        return experimentClassesService.findAll(new PageRequest(page, size));
    }

    /**
     * 根据课程id查看课程详细信息
     * @param id
     * @return
     */
    @RequestMapping("/findExpClassById")
    public ExperimentClasses findExpClassById(Integer id) {
        return experimentClassesService.findById(id);
    }

    /**
     * 选课动作
     * @param id
     * @return
     */
    @RequestMapping("/selectExpClassById")
    public Map<Object, Object> selectExpClassById(Integer id, Authentication authentication) {
        String username = (String)authentication.getPrincipal();
        return null;
    }
}
