package com.stpl.edurp.models;

/**
 * Created by Admin on 28-01-2017.
 */

public class TableUserMasterDataModel {
    private String UserId;
    private String UserName;
    private String UserType;
    private String Designation;
    private String ImageURL;

    public TableUserMasterDataModel() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
