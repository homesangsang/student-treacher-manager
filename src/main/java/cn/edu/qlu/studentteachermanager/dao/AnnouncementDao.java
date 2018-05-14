package cn.edu.qlu.studentteachermanager.dao;

import cn.edu.qlu.studentteachermanager.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnnouncementDao extends PagingAndSortingRepository<Announcement, Integer>, JpaRepository<Announcement, Integer>{
    Announcement findById(Integer id);
}
