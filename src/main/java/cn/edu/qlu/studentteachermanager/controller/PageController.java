package cn.edu.qlu.studentteachermanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("editStudentPage")
    public String editPage(){
        return "editStudent";
    }
}
