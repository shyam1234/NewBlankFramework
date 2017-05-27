package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 30-12-2016.
 */

public class LoginDataModel extends IModel {

    @SerializedName("Status")
    public boolean Status;
    @SerializedName("Data")
    public Data data;
    @SerializedName("University")
    public ArrayList<University> universityArrayList;
    @SerializedName("ParentProfile")
    public ArrayList<ParentProfile> parentProfileArrayList;
    @SerializedName("StudentProfiles")
    public ArrayList<StudentProfiles> studentProfilesArrayList;
    @SerializedName("ParentStudentAssociation")
    public ArrayList<ParentStudentAssociation> parentStudentAssociationArrayList;
    @SerializedName("ParentStudentMenuDetails")
    public ArrayList<ParentStudentMenuDetails> parentStudentMenuDetailsArrayList;

    public LoginDataModel() {
        data = new Data();
        universityArrayList = new ArrayList<University>();
        parentProfileArrayList = new ArrayList<ParentProfile>();
        studentProfilesArrayList = new ArrayList<StudentProfiles>();
        parentStudentAssociationArrayList = new ArrayList<ParentStudentAssociation>();
        parentStudentMenuDetailsArrayList = new ArrayList<ParentStudentMenuDetails>();
    }


    public static class Data extends IModel {
        /*{
        "Data":{
        "UserId":43,
        "UniversityId":1,
        "UserName":"Admin1",
        "EmailAddress":"a1@a.com",
        "PhoneNumber":"858585858",
        "Password":"login@123",
        "LastLogin":null,
        "IsActive":true,
        "IsLocked":false,
        "CreatedOn":"2016-12-24T14:33:35.067",
        "PassAttemptCount":0,
        "AuthenticationToken":null,
        "Status":"C",
        "UserStatus":"Completed",
        "LastLoginIPAddress":null,
        "IsDeleted":null,
        "Address":null,
        "CityId":null,
        "CityName":null,
        "Districtid":null,
        "DistrictName":null,
        "StateId":null,
        "StateName":null,
        "CountryId":null,
        "DefaultLanguage":"B",
        "UserTypeId":88,
        "DeviceType":null,
        "UserType":"Admin",
        "DeviceToken":null,
        "DeviceIdentifier":null,
        "ATTENDANCEDATE":0,
        "RoleCode":"ADMIN,OPERATOR",
        "RoleId":"1,2"
        }
       }*/


        @SerializedName("UserId")
        public int UserId;
        @SerializedName("UniversityId")
        public int UniversityId;
        @SerializedName("UserName")
        public String UserName;
        @SerializedName("EmailAddress")
        public String EmailAddress;
        @SerializedName("PhoneNumber")
        public String PhoneNumber;
        @SerializedName("LastLogin")
        public String LastLogin;
        @SerializedName("IsActive")
        public boolean IsActive;
        @SerializedName("IsLocked")
        public boolean IsLocked;
        @SerializedName("CreatedOn")
        public String CreatedOn;
        @SerializedName("PassAttemptCount")
        public int PassAttemptCount;
        @SerializedName("AuthenticationToken")
        public String AuthenticationToken;
        @SerializedName("Status")
        public boolean Status;
        @SerializedName("UserStatus")
        public String UserStatus;
        @SerializedName("LastLoginIPAddress")
        public String LastLoginIPAddress;
        @SerializedName("IsDeleted")
        public String IsDeleted;
        @SerializedName("Address")
        public String Address;
        @SerializedName("CityId")
        public String CityId;
        @SerializedName("CityName")
        public String CityName;
        @SerializedName("Districtid")
        public String Districtid;
        @SerializedName("DistrictName")
        public String DistrictName;
        @SerializedName("StateId")
        public String StateId;
        @SerializedName("StateName")
        public String StateName;
        @SerializedName("CountryId")
        public String CountryId;
        @SerializedName("DefaultLanguage")
        public String DefaultLanguage;
        @SerializedName("UserTypeId")
        public String UserTypeId;
        @SerializedName("DeviceType")
        public String DeviceType;
        @SerializedName("UserType")
        public String UserType;
        @SerializedName("DeviceToken")
        public String DeviceToken;
        @SerializedName("DeviceIdentifier")
        public String DeviceIdentifier;
        @SerializedName("ATTENDANCEDATE")
        public String TOTAL;
        @SerializedName("RoleCode")
        public String RoleCode;
        @SerializedName("RoleId")
        public String RoleId;
        @SerializedName("test")
        public String test;

    }


    //University----------------------------------

    public static class University {
        /*[{
          "UniversityId": 1,
          "UniversityName": "Universitas Muhammadiyah Palangkaraya1",
          "UniversityCode": "UMP2",
          "UniversityURL": "UMP",
          "UniversityLogoPath": null
       }]*/
        @SerializedName("UniversityId")
        public int UniversityId;
        @SerializedName("UniversityName")
        public String UniversityName;
        @SerializedName("UniversityCode")
        public String UniversityCode;
        @SerializedName("UniversityURL")
        public String UniversityURL;
        @SerializedName("UniversityLogoPath")
        public String UniversityLogoPath;
    }


    public static class ParentProfile {
        /*[{
                "ParentId": 118,
                "ParentName": "Parent 1",
                "EmailAddress": "p1@p.com",
                "PhoneNumber": "99999999",
                "ImageURL": null
        }]*/

        @SerializedName("ParentId")
        public int ParentId;
        @SerializedName("ParentName")
        public String ParentName;
        @SerializedName("EmailAddress")
        public String EmailAddress;
        @SerializedName("PhoneNumber")
        public String PhoneNumber;
        @SerializedName("ImageURL")
        public String ImageURL;
    }

    public static class StudentProfiles {
        /* [{
             "StudentId": 7,
                 "UniversityId": 1,
                 "StudentNumber": "16.ADNA.000007",
                 "FullName": "Merie Elita",
                 "DateOfBirth": "01-Jan-2016",
                 "Gender": "Female",
                 "CourseCode": "Mechanical",
                 "ImageURL": null
         }]*/
        @SerializedName("StudentId")
        public int StudentId;
        @SerializedName("UniversityId")
        public int UniversityId;
        @SerializedName("StudentNumber")
        public String StudentNumber;
        @SerializedName("FullName")
        public String FullName;
        @SerializedName("DateOfBirth")
        public String DateOfBirth;
        @SerializedName("Gender")
        public String Gender;
        @SerializedName("CourseCode")
        public String CourseCode;
        @SerializedName("ImageURL")
        public String ImageURL;
    }


    public static class ParentStudentAssociation {
        /* [{
             "ParentId": 118,
                 "StudentId": 7,
                 "IsDefault": true
         }]*/
        @SerializedName("ParentId")
        public int ParentId;
        @SerializedName("StudentId")
        public int StudentId;
        @SerializedName("IsDefault")
        public boolean IsDefault;

    }

    public static class ParentStudentMenuDetails {
        /*[{
          "ParentId": 118,
          "StudentId": 7,
          "UniversityId": 1,
          "SubscriptionCode": "NEW",
          "IsActive": 1,
          "ColumnCount": 2
        }]*/

        @SerializedName("ParentId")
        public int ParentId;
        @SerializedName("StudentId")
        public int StudentId;
        @SerializedName("UniversityId")
        public int UniversityId;
        @SerializedName("SubscriptionCode")
        public String SubscriptionCode;
        @SerializedName("IsActive")
        public int IsActive;
        @SerializedName("ColumnCount")
        public int ColumnCount;
    }
}
