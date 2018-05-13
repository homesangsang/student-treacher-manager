package cn.edu.qlu.studentteachermanager.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 学生表
 */
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Integer id;

    private String name; // 学生姓名

    private String snumber; // 学号

    private String sex; // 性别

    private String department; // 部门

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students", cascade = CascadeType.REMOVE)
    private List<ExperimentClasses> experimentClasses; // 一个学生可以选多门课程

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", snumber='" + snumber + '\'' +
                ", sex='" + sex + '\'' +
                ", department='" + department + '\'' +
                ", experimentClasses=" + experimentClasses +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnumber() {
        return snumber;
    }

    public void setSnumber(String snumber) {
        this.snumber = snumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<ExperimentClasses> getExperimentClasses() {
        return experimentClasses;
    }

    public void setExperimentClasses(List<ExperimentClasses> experimentClasses) {
        this.experimentClasses = experimentClasses;
    }


}
