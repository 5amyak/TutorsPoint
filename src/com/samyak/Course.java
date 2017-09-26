package com.samyak;

public class Course {
    private int courseId;
    private int teacherId;
    private String courseName;
    private int courseRating;

    public Course(int courseId, int teacherId, String courseName, int courseRating) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.courseName = courseName;
        this.courseRating = courseRating;
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

    public int getCourseRating() {
        return courseRating;
    }
}
