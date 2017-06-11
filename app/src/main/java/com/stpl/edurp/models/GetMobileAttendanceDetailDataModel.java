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
        @SerializedName("StudentAttendanceDetailList")
        private ArrayList<TableAttendanceDetailsDataModel> StudentAttendanceDetailList ;

        public ArrayList<TableAttendanceDetailsDataModel> getStudentAttendanceDetailList() {
            return StudentAttendanceDetailList;
        }

        public ArrayList<TableCourseMasterDataModel> getStudentDetailList() {
            return StudentDetailList;
        }

    }


}
