package com.aaa;

import lombok.Data;

import java.util.List;

@Data
public class Clazz {
    private Integer cid;
    private String cname;
    private Integer teacherId;
    private Teacher teacher; // 该班级对应的教室信息
    private List<Student> students; // 该班级下包含的学生
}
