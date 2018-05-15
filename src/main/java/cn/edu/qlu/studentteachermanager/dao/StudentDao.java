package cn.edu.qlu.studentteachermanager.dao;

import cn.edu.qlu.studentteachermanager.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentDao extends PagingAndSortingRepository<Student, Integer>, JpaRepository<Student, Integer>{
    Student findById(Integer id);
    Student findBySnumber(String username);
}
