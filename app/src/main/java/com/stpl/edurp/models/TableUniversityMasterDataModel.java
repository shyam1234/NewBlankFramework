package com.stpl.edurp.models;

/**
 * Created by Admin on 14-01-2017.
 */

public class TableUniversityMasterDataModel {
    private int university_id;
    private String university_name = "";
    private String university_url = "";
    private String university_code = "";
    private String university_logo_path = "";

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

    public String getUniversity_url() {
        return university_url;
    }

    public void setUniversity_url(String university_url) {
        this.university_url = university_url;
    }

    public String getUniversity_code() {
        return university_code;
    }

    public void setUniversity_code(String university_code) {
        this.university_code = university_code;
    }

    public String getUniversity_logo_path() {
        return university_logo_path;
    }

    public void setUniversity_logo_path(String university_logo_path) {
        this.university_logo_path = university_logo_path;
    }
}
