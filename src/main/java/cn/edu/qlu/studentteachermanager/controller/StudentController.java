package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.entity.Announcement;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import cn.edu.qlu.studentteachermanager.message.ResultMessage;
import cn.edu.qlu.studentteachermanager.service.AnnouncementService;
import cn.edu.qlu.studentteachermanager.service.ExperimentClassesService;
import cn.edu.qlu.studentteachermanager.service.StudentService;
import javassist.compiler.ast.StringL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.ManyToMany;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Secured("ROLE_STUDENT")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ExperimentClassesService experimentClassesService;

    @Autowired
    private StudentService studentService;

    /**
     * 查看公告
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/selectAnno")
    public ResultMessage selectAnnoByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (page > 0) {
            page--;
        }
        Page<Announcement> pages = announcementService.findAll(new PageRequest(page, size));
        return new ResultMessage(200, pages.getContent(), pages.getTotalElements(), "success");
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
    public ResultMessage selectExpClassByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "0") Integer size
    ) {
        if (page > 0) {
            page--;
        }
        Page<ExperimentClasses> pages = experimentClassesService.findAll(new PageRequest(page, size));
        return new ResultMessage(200, pages.getContent(), pages.getTotalElements(), "success");
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
        studentService.selectExpClassById(id, username);
        Map<Object, Object> map = new HashMap<>();
        map.put("result","true");
        return map;
    }

    /**
     * 查看已选课程列表
     * @param authentication
     * @return
     */
    @GetMapping("/findSelectedExp")
    public ResultMessage findSelectedExp(Authentication authentication,
                                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        String username = (String) authentication.getPrincipal();
        List<ExperimentClasses> list =  studentService.findSelectedExp(username);
        return new ResultMessage(200, list, Long.parseLong(String.valueOf(list.size())), "success");
    }

    @GetMapping("/deleteSelectedById")
    public String deleteSelected(Authentication authentication,Integer id) {
        String username = (String) authentication.getPrincipal();
        studentService.deleteExpBySnumber(username, id);
        return "success";
    }
}
