package com.example.student_accounting_journal;

import java.util.List;

public class Group {
    private String name;
    private float rate;
    private List<Student> students;

    public Group() {}

    public Group(String name, float rate, List<Student> students) {
        this.name = name;
        this.rate = rate;
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
