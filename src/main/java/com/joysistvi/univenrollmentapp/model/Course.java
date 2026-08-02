package com.joysistvi.univenrollmentapp.model;

// Model Class
// Represents a Course entity
// Uses encapsulation to store course information
public class Course {

    // Fields
    private final int id;
    private final String courseCode;
    private final String courseName;
    private final int units;
    private final int departmentId;
    private final String departmentName;

    // Constructor
    // Used when department name is not needed
    public Course(
            int id,
            String courseCode,
            String courseName,
            int units,
            int departmentId) {

        this(
                id,
                courseCode,
                courseName,
                units,
                departmentId,
                null);

    }

    // Constructor
    // Used when retrieving courses together with the department name
    public Course(
            int id,
            String courseCode,
            String courseName,
            int units,
            int departmentId,
            String departmentName) {

        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.units = units;
        this.departmentId = departmentId;
        this.departmentName = departmentName;

    }

    // Get the course ID
    public int getId() {
        return id;
    }

    // Get the course code
    public String getCourseCode() {
        return courseCode;
    }

    // Get the course name
    public String getCourseName() {
        return courseName;
    }

    // Get the number of units
    public int getUnits() {
        return units;
    }

    // Get the department ID
    public int getDepartmentId() {
        return departmentId;
    }

    // Get the department name
    public String getDepartmentName() {
        return departmentName;
    }

}