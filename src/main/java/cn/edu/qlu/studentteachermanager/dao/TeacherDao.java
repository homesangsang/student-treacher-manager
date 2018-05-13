package cn.edu.qlu.studentteachermanager.dao;

import cn.edu.qlu.studentteachermanager.entity.Student;
import cn.edu.qlu.studentteachermanager.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeacherDao extends PagingAndSortingRepository<Teacher, Integer>, JpaRepository<Teacher, Integer>{
    Teacher findById(Integer id);

}
