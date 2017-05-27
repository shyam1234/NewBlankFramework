package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 31-03-2017.
 */

public class   GetMobileAttendanceDetailDataModel extends IModel{
    @SerializedName("MessageResult")
    private String MessageResult;
    @SerializedName("MessageBody")
    private MessageBodyDataModel MessageBody = new MessageBodyDataModel();


    public String getMessageResult() {
        return MessageResult;
    }

    public MessageBodyDataModel getMessageBody() {
        return MessageBody;
    }


    public class MessageBodyDataModel {
        @SerializedName("StudentDetailList")
        private ArrayList<TableCourseMasterDataModel> StudentDetailList;
        //private ArrayList<StudentDetailListDataModel> StudentDetailList;

        @SerializedName("StudentAttendanceDetailList")
        private ArrayList<TableAttendanceDetailsDataModel> StudentAttendanceDetailList ;

        public ArrayList<TableAttendanceDetailsDataModel> getStudentAttendanceDetailList() {
            return StudentAttendanceDetailList;
        }

        public ArrayList<TableCourseMasterDataModel> getStudentDetailList() {
            return StudentDetailList;
        }

        public void setStudentDetailList(ArrayList<TableCourseMasterDataModel> studentDetailList) {
            StudentDetailList = studentDetailList;
        }
    }

//    public static class StudentDetailListDataModel implements Serializable {
//        /* {
//        "StudentId": 335,
//        "Course": "CSE-A",
//        "Semester": "Semester 2",
//        "ReferenceId": 2428
//        "LastRetrieved": 20170428
//      }*/
//        @SerializedName("StudentId")
//        private int StudentId;
//        @SerializedName("Course")
//        private String Course;
//        @SerializedName("Semester")
//        private String Semester;
//        @SerializedName("ReferenceId")
//        private int ReferenceId;
//        @SerializedName("LastRetrieved")
//        private String LastRetrieved;
//
//        public int getStudentId() {
//            return StudentId;
//        }
//
//        public void setStudentId(int studentId) {
//            StudentId = studentId;
//        }
//
//        public String getCourse() {
//            return Course;
//        }
//
//        public void setCourse(String course) {
//            Course = course;
//        }
//
//        public String getSemester() {
//            return Semester;
//        }
//
//        public void setSemester(String semester) {
//            Semester = semester;
//        }
//
//        public int getReferenceId() {
//            return ReferenceId;
//        }
//
//        public void setReferenceId(int referenceId) {
//            ReferenceId = referenceId;
//        }
//
//        public String getLastRetrieved() {
//            return LastRetrieved;
//        }
//
//        public void setLastRetrieved(String lastRetrieved) {
//            LastRetrieved = lastRetrieved;
//        }
//    }


}
