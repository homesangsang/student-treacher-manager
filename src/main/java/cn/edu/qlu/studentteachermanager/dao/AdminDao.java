package cn.edu.qlu.studentteachermanager.dao;

import cn.edu.qlu.studentteachermanager.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdminDao extends PagingAndSortingRepository<Admin, Integer>, JpaRepository<Admin, Integer>{
}
