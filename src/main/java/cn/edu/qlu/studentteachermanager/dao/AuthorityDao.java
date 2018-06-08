package cn.edu.qlu.studentteachermanager.dao;

import cn.edu.qlu.studentteachermanager.entity.Admin;
import cn.edu.qlu.studentteachermanager.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-05-23
 * Time: 11:52
 */
public interface AuthorityDao extends PagingAndSortingRepository<Authority, Integer>, JpaRepository<Authority, Integer> {
    Authority findById(Integer id);
}
