package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.Admin;
import cn.edu.qlu.studentteachermanager.entity.PasswordContent;
import cn.edu.qlu.studentteachermanager.entity.Student;
import cn.edu.qlu.studentteachermanager.entity.Teacher;
import cn.edu.qlu.studentteachermanager.message.ResultMessage;
import cn.edu.qlu.studentteachermanager.service.AdminService;
import cn.edu.qlu.studentteachermanager.service.StudentService;
import cn.edu.qlu.studentteachermanager.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(AdminController.class);


    /**
     * 添加学生的功能
     * @param student
     * @return
     */
    @PostMapping("/addStudent")
    public Map<Object, Object> addStudent(@RequestBody Student student) {
        Map<Object, Object> result = new HashMap<>();
        studentService.addStudent(student);
        result.put("result", true);
        result.put("message", "success!");
        return result;
    }

    /**
     * 删除学生，如果学生已经选课，则返回错误码，不删除学生；如果学生还没有选课，则可以删除
     * @param student
     * @return
     */
    @PostMapping(value = "/delStudent")
    public String delStudent(@RequestBody Student student) {
        studentService.deleteStudentById(student);
        return "成功!";
    }

    /**
     * 删除老师，如果老师已经发布选课，则返回错误码，不删除老师；如果老师还没有发布课程，则可以删除
     * @param teacher
     * @return
     */
    @PostMapping(value = "/delTeacher")
    public String delTeacher(@RequestBody Teacher teacher) {
        teacherService.deleteTeacher(teacher);
        logger.info(teacher.toString());
        return "成功!";
    }
    /**
     * 添加老师的功能
     * @param teacher
     * @return
     */
    @PostMapping("/addTeacher")
    public Map<Object, Object> addTeacher(@RequestBody Teacher teacher) {
        Map<Object,Object> result = new HashMap<>();
        teacherService.addTeacher(teacher);
        result.put("result",true);
        result.put("message", "success!\n" + teacher.toString());
        return result;
    }

    /**
     * 添加管理员的功能
     * @param admin
     * @return
     */
    @PostMapping("/addAdmin")
    public Map<Object, Object> addAdmin(Admin admin) {
        return null;
    }

    /**
     * 查询学生列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/getStudentList")
    public ResultMessage getStudentListByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (page > 0) {
            page--;
        }
        Page<Student> pages = studentService.findAllStudent(new PageRequest(page, size));
        return new ResultMessage(200, pages.getContent(), pages.getTotalElements(), "success");
    }

    /**
     * 查询老师列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/getTeacherList")
    public ResultMessage getTeacherListByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (page > 0) {
            page--;
        }
        Page<Teacher> pages =  teacherService.findAllTeacher(new PageRequest(page, size));
        return new ResultMessage(200, pages.getContent(), pages.getTotalElements(), "success");
    }

    /**
     * 通过"详情"按钮查询学生详细信息
     * @param id
     * @return
     */
    @GetMapping("/getStudentInfo")
    public Student getStudentById(@RequestParam(value = "id" ) Integer id) {
         return studentService.getStudentById(id);
    }
    /**
     * 通过"详情"按钮查询老师详细信息
     * @param id
     * @return
     */
    @GetMapping("/getTeacherInfo")
    public Teacher getTeacherById(@RequestParam(value = "id") Integer id) {
        return teacherService.getTeacherById(id);
    }

    /**
     * 重置密码
     * @param passes
     * @param authentication
     * @return
     */
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestBody PasswordContent passes, Authentication authentication) {
        String username = (String)authentication.getPrincipal();
        return adminService.updatePassword(username, passes);
    }
}
