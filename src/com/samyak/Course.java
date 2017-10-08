package com.samyak;

public class Course {
    private int courseId;
    private int teacherId;
    private String courseName;

    public Course(int courseId, int teacherId, String courseName) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return getCourseName();
    }

    public int getCourseId() {
        return courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

}
