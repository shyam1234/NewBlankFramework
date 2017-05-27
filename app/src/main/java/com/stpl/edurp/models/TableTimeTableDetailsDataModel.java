package com.stpl.edurp.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class TableTimeTableDetailsDataModel extends IModel {
    @SerializedName("MessageResult")
    private String MessageResult;
    @SerializedName("MessageBody")
    private ArrayList<InnerTimeTableDetailDataModel> MessageBody = new ArrayList<InnerTimeTableDetailDataModel>();

    public String getMessageResult() {
        return MessageResult;
    }

    public ArrayList<InnerTimeTableDetailDataModel> getMessageBody() {
        return MessageBody;
    }


    public static class InnerTimeTableDetailDataModel extends IModel implements Comparable<InnerTimeTableDetailDataModel> {
        @SerializedName("MenuCode")
        private String MenuCode;
        @SerializedName("StudentId")
        private int StudentId;
        @SerializedName("ReferenceDate")
        private String ReferenceDate;
        @SerializedName("SubjectName")
        private String SubjectName;
        @SerializedName("TTime")
        private String TTime;
        @SerializedName("Faculty")
        private String Faculty;
        @SerializedName("RoomName")
        private String RoomName;
        @SerializedName("IsPresent")
        private String IsPresent;
        @SerializedName("SqOrder")
        private String SqOrder;

        public String getMenuCode() {
            return MenuCode;
        }

        public void setMenuCode(String menuCode) {
            MenuCode = menuCode;
        }

        public int getStudentId() {
            return StudentId;
        }
        public void setStudentId(int studentId) {

            StudentId = studentId;
        }

        public String getReferenceDate() {
            return ReferenceDate;
        }

        public void setReferenceDate(String referenceDate) {
            ReferenceDate = referenceDate;
        }

        public String getSubjectName() {
            return SubjectName;
        }

        public void setSubjectName(String subjectName) {
            SubjectName = subjectName;
        }

        public String getTTime() {
            return TTime;
        }

        public void setTTime(String TTime) {
            this.TTime = TTime;
        }

        public String getFaculty() {
            return Faculty;
        }

        public void setFaculty(String faculty) {
            Faculty = faculty;
        }

        public String getRoomName() {
            return RoomName;
        }

        public void setRoomName(String roomName) {
            RoomName = roomName;
        }

        public String getIsPresent() {
            return IsPresent;
        }

        public void setIsPresent(String isPresent) {
            IsPresent = isPresent;
        }

        public String getSqOrder() {
            return SqOrder;
        }

        public void setSqOrder(String sqOrder) {
            SqOrder = sqOrder;
        }

        @Override
        public int compareTo(@NonNull InnerTimeTableDetailDataModel o) {
            try {
                return getSqOrder().compareTo(o.getSqOrder());
            } catch (Exception e) {
                AppLog.errLog("InnerTimeTableDetailDataModel", e.getMessage());
            }
            return 0;
        }

    }
}
