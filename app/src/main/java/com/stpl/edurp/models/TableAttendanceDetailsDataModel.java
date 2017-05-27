package com.stpl.edurp.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.io.Serializable;

/**
 * Created by Admin on 21-01-2017.
 */

public class TableAttendanceDetailsDataModel extends IModel implements Comparable<TableAttendanceDetailsDataModel>, Serializable {

    @SerializedName("SubjectId")
    private int SubjectId;
    @SerializedName("Course")
    private String Course;
    @SerializedName("Total")
    private String Total;
    @SerializedName("Present")
    private String Present;
    @SerializedName("Absent")
    private String Absent;
    @SerializedName("Percentage")
    private String Percentage;
    @SerializedName("Color")
    private String Color;
    @SerializedName("ReferenceId")
    private int ReferenceId;
    @SerializedName("Subject")
    private String Subject;
    private String Semester;

    public int getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(int subjectId) {
        SubjectId = subjectId;
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

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getPresent() {
        return Present;
    }

    public void setPresent(String present) {
        Present = present;
    }

    public String getAbsent() {
        return Absent;
    }

    public void setAbsent(String absent) {
        Absent = absent;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }


    @Override
    public int compareTo(@NonNull TableAttendanceDetailsDataModel o) {
        return 0;
    }

    public int getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(int referenceId) {
        ReferenceId = referenceId;
    }
}
