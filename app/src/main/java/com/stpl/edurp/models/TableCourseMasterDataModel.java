package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 21-01-2017.
 */

public class TableCourseMasterDataModel  implements Serializable {

    /* {
        "StudentId": 335,
        "Course": "CSE-A",
        "Semester": "Semester 2",
        "ReferenceId": 2428
            "LastRetrieved": 20170428
      }*/
    @SerializedName("StudentId")
    private int StudentId;
    @SerializedName("Course")
    private String Course;
    @SerializedName("Semester")
    private String Semester;
    @SerializedName("LastRetrieved")
    private String LastRetrieved;
    @SerializedName("ReferenceId")
    private int ReferenceId;


    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getLastRetrieved() {
        return LastRetrieved;
    }

    public void setLastRetrieved(String lastRetrieved) {
        LastRetrieved = lastRetrieved;
    }

    public int getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(int referenceId) {
        ReferenceId = referenceId;
    }
}
