package cn.edu.qlu.studentteachermanager.dao;

import cn.edu.qlu.studentteachermanager.entity.ExperimentClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExperimentClassesDao extends PagingAndSortingRepository<ExperimentClasses, Integer>, JpaRepository<ExperimentClasses, Integer> {
}
