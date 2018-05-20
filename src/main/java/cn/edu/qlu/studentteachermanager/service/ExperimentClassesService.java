package cn.edu.qlu.studentteachermanager.service;

import cn.edu.qlu.studentteachermanager.dao.ExperimentClassesDao;
import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Description:
 */
@Service
public class ExperimentClassesService {

    @Autowired
    private ExperimentClassesDao experimentClassesDao;

    /**
     * 分页显示课程列表
     * @param pageRequest
     * @return
     */
    public Page<ExperimentClasses> findAll(PageRequest pageRequest) {
        return experimentClassesDao.findAll(pageRequest);
    }

    /**
     * 查询课程详细信息
     * @param id
     * @return
     */
    public ExperimentClasses findById(Integer id) {
        return experimentClassesDao.findById(id);
    }

    public ExperimentClasses save(ExperimentClasses experimentClasses) {
        return experimentClassesDao.saveAndFlush(experimentClasses);
    }

}
