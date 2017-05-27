package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 30-12-2016.
 */

public class GetMobileHomeDataModel extends IModel {

    @SerializedName("University")
    public ArrayList<LoginDataModel.University> universityArrayList;
    @SerializedName("ParentProfile")
    public ArrayList<LoginDataModel.ParentProfile> parentProfileArrayList;
    @SerializedName("StudentProfiles")
    public ArrayList<LoginDataModel.StudentProfiles> studentProfilesArrayList;
    @SerializedName("ParentStudentAssociation")
    public ArrayList<LoginDataModel.ParentStudentAssociation> parentStudentAssociationArrayList;
    @SerializedName("ParentStudentMenuDetails")
    public ArrayList<LoginDataModel.ParentStudentMenuDetails> ParentStudentMenuDetailsArrayList;


    public GetMobileHomeDataModel() {
        universityArrayList = new ArrayList<LoginDataModel.University>();
        parentProfileArrayList = new ArrayList<LoginDataModel.ParentProfile>();
        studentProfilesArrayList = new ArrayList<LoginDataModel.StudentProfiles>();
        parentStudentAssociationArrayList = new ArrayList<LoginDataModel.ParentStudentAssociation>();
        ParentStudentMenuDetailsArrayList = new ArrayList<LoginDataModel.ParentStudentMenuDetails>();
    }

}
