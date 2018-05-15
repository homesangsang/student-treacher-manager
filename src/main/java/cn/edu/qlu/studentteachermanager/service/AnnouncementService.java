package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.AnnouncementDao;
import cn.edu.qlu.studentteachermanager.entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 桑智勇(homesangsang)
 * Date: 2018-05-14
 * Time: 8:51
 */
@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementDao announcementDao;

    /**
     * 分页查询公告
     * @param pageRequest
     * @return
     */
    public Page<Announcement> findAll(PageRequest pageRequest) {
        return announcementDao.findAll(pageRequest);
    }

    /**
     * 根据id查找公告，查看公告详情
     * @param id
     * @return
     */
    public Announcement findAnnById(Integer id) {
        return announcementDao.findById(id);
    }

    public Announcement save(Announcement announcement) {
        return announcementDao.save(announcement);
    }
}
