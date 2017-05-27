package com.stpl.edurp.models;

/**
 * Created by Admin on 14-01-2017.
 */

public class TableParentStudentMenuDetailsDataModel {
    private int alert_count;
    //private String date_updated = "";
    private String menu_code = "";
    private int parent_id;
    private int student_id;
    private int UniversityId;
    private int IsActive;

    public int getAlert_count() {
        return alert_count;
    }

    public void setAlert_count(int alert_count) {
        this.alert_count = alert_count;
    }

    // public String getDate_updated() {
    //  return date_updated;
    //}

    // public void setDate_updated(String date_updated) {
    //     this.date_updated = date_updated;
    // }

    public String getMenu_code() {
        return menu_code;
    }

    public void setMenuCode(String menu_code) {
        this.menu_code = menu_code;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParentId(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getStudentId() {
        return student_id;
    }

    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }

    public int getUniversityId() {
        return UniversityId;
    }

    public void setUniversityId(int universityId) {
        UniversityId = universityId;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }
}
