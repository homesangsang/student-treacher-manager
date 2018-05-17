package cn.edu.qlu.studentteachermanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * 实验课
 */
@Entity
public class ExperimentClasses {
    @Id
    @GeneratedValue
    private Integer id;

    private String name; // 实验课名称

    private String location; // 上课地点

    private String time; // 时间

    private String hour; // 学时

    private String credit; // 学时

    private Integer sum; // 开课人数

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Student> students; // 一门课程有许多学生

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Teacher> teachers; // 多个老师可以教同一门课程（助教）

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<Student> getStudents() {
        return students;
    }

    @JsonBackReference
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "ExperimentClasses{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                ", hour='" + hour + '\'' +
                ", credit='" + credit + '\'' +
                ", sum=" + sum +
                '}';
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    @JsonBackReference
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
