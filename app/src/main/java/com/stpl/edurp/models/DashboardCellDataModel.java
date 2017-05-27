package com.stpl.edurp.models;

/**
 * Created by Admin on 11-12-2016.
 */

public class DashboardCellDataModel {
    private int menuImage;
    private String text;
    private int alert_count;
    private String date_updated;
    private String menu_code;
    private int parent_id;
    private int student_id;
    private int university_id;
    private String university_name;
    private String university_url="";
    private int color;
    private int isActive;
    private String studentProfileImage = "";

    public int getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(int menuImage) {
        this.menuImage = menuImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNotification() {
        return alert_count;
    }

    public void setNotification(int notification) {
        this.alert_count = notification;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getMenu_code() {
        return menu_code;
    }

    public void setMenu_code(String menu_code) {
        this.menu_code = menu_code;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParentId(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }

    public int getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(int university_id) {
        this.university_id = university_id;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getUniversity_url() {
        return university_url;
    }

    public void setUniversity_url(String university_url) {
        this.university_url = university_url;
    }

    public String getStudentProfileImage() {
        return studentProfileImage;
    }

    public void setStudentProfileImage(String studentProfileImage) {
        this.studentProfileImage = studentProfileImage;
    }
}
