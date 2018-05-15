package cn.edu.qlu.studentteachermanager.controller;

import cn.edu.qlu.studentteachermanager.dao.UserDao;
import cn.edu.qlu.studentteachermanager.entity.Admin;
import cn.edu.qlu.studentteachermanager.entity.Student;
import cn.edu.qlu.studentteachermanager.entity.Teacher;
import cn.edu.qlu.studentteachermanager.service.StudentService;
import cn.edu.qlu.studentteachermanager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public Page<Student> getStudentListByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return studentService.findAllStudent(new PageRequest(page, size));
    }

    /**
     * 查询老师列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/getTeacherList")
    public Page<Teacher> getTeacherListByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return teacherService.findAllTeacher(new PageRequest(page, size));
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
}
