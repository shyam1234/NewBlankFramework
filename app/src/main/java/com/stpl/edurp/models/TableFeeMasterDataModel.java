package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.utils.AppLog;

/**
 * Created by Admin on 05-02-2017.
 */

public class TableFeeMasterDataModel implements Comparable<TableFeeMasterDataModel>
{
        /*{
           "MenuCode":"FEE",
           "ParentId":239,
           "StudentId":328,
           "ReferenceId":411,
           "StudentNumber":"16.72.017385",
           "StudentName":"NOOR IDHA",
           "CourseName":"Administrasi Negara -A Section",
           "SemesterName":"Semester 1",
           "TotalDue":"Rp 1842400.00",
           "DueDate":"20170220000000",
           "PublishedOn":"20170304093851",
           "PublishedBy":"Admin",
           "ExpiryDate":"20170314075353",
           "Status":"Not Paid"
         },*/


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
    @SerializedName("CourseName")
    private String CourseName;
    @SerializedName("SemesterName")
    private String SemsterName;
    @SerializedName("TotalDue")
    private String TotalDue;
    @SerializedName("DueDate")
    private String DueDate;
    @SerializedName("PublishedOn")
    private String PublishedOn;
    @SerializedName("PublishedBy")
    private String PublishedBy;
    @SerializedName("ExpiryDate")
    private String ExpiryDate;
    @SerializedName("Status")
    private String Status="";
    @SerializedName("FeeTitle")
    private String FeeTitle;



    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String menuCode) {
        MenuCode = menuCode;
    }

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

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getSemsterName() {
        return SemsterName;
    }

    public void setSemsterName(String semsterName) {
        SemsterName = semsterName;
    }

    public String getTotalDue() {
        return TotalDue;
    }

    public void setTotalDue(String totalDue) {
        TotalDue = totalDue;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
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

    public String getFeeTitle() {
        return FeeTitle;
    }

    public void setFeeTitle(String feeTitle) {
        this.FeeTitle = feeTitle;
    }


    @Override
    public int compareTo(TableFeeMasterDataModel o) {
        try {
            return getPublishedOn().compareTo(o.getPublishedOn());
        }catch (Exception e){
            AppLog.errLog("TableFeeMasterDataModel",e.getMessage());
        }
        return 0;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
