package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 25-02-2017.
 */

public class TableResultMasterDataModel extends Object implements Serializable{

    /*
    {
    "MenuCode":"RES",
    "ParentId":239,
    "StudentId":328,
    "ReferenceId":2369,
    "StudentNumber":"17.ADNA.000325",
    "StudentName":"varma",
    "AcademicYear":"BAT201718ENGG",
    "CourseName":"Administrasi Negara -A Section",
    "SemesterName":"Semester 1",
    "AchivementIndex":"1.46",
    "Result":"Fail",
    "PublishedOn":"20170304093851",
    "PublishedBy":"Admin",
    "ExpiryDate":"20170314075353"
    },
  */


    @SerializedName("MenuCode")
    private String MenuCode;
    @SerializedName("ParentId")
    private int ParentId;
    @SerializedName("StudentId")
    private int StudentId;
    @SerializedName("ReferenceId")
    private int ReferenceId;
    @SerializedName("StudentNumber")
    private String StudentNumber;
    @SerializedName("StudentName")
    private String StudentName;
    @SerializedName("AcademicYear")
    private String AcademicYear;
    @SerializedName("CourseName")
    private String CourseName;
    @SerializedName("SemesterName")
    private String SemesterName;
    @SerializedName("AchivementIndex")
    private String AchievementIndex;
    @SerializedName("Result")
    private String Result;
    @SerializedName("PublishedOn")
    private String PublishedOn;
    @SerializedName("PublishedBy")
    private String PublishedBy;
    @SerializedName("ExpiryDate")
    private String ExpiryDate;


    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(int referenceId) {
        ReferenceId = referenceId;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }

    public void setAcademicYear(String academicYear) {
        AcademicYear = academicYear;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getSemesterName() {
        return SemesterName;
    }

    public void setSemesterName(String semesterName) {
        SemesterName = semesterName;
    }

    public String getAchievementIndex() {
        return AchievementIndex;
    }

    public void setAchievementIndex(String achievementIndex) {
        AchievementIndex = achievementIndex;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getPublishedOn() {
        return PublishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        PublishedOn = publishedOn;
    }

    public String getPublishedBy() {
        return PublishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        PublishedBy = publishedBy;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String menuCode) {
        MenuCode = menuCode;
    }
}

