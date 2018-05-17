package cn.edu.qlu.studentteachermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * 老师表
 */
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private Integer id;

    private String name; // 老师姓名

    private String tnumber; // 工号

    private String sex; // 性别

    private String department; // 部门

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Announcement> announcementList; // 教师发送公告的列表

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "teachers", cascade = CascadeType.REMOVE)
    private List<ExperimentClasses> experimentClasses;

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

    public String getTnumber() {
        return tnumber;
    }

    public void setTnumber(String tnumber) {
        this.tnumber = tnumber;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tnumber='" + tnumber + '\'' +
                ", sex='" + sex + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }
}
