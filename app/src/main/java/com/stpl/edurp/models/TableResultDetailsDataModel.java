package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class TableResultDetailsDataModel extends IModel {


    @SerializedName("MessageBody")
    private ArrayList<InnerResultDetails> MessageBody = new ArrayList<InnerResultDetails>();

    public ArrayList<InnerResultDetails> getMessageBody() {
        return MessageBody;
    }


    public static class InnerResultDetails {
        @SerializedName("ReferenceId")
        private int ReferenceId;
        @SerializedName("SubjectName")
        private String SubjectName;
        @SerializedName("Credits")
        private String Credits;
        @SerializedName("Grade")
        private String Grade;
        @SerializedName("Result")
        private String Result;


        public int getReferenceId() {
            return ReferenceId;
        }

        public void setReferenceId(int referenceId) {
            ReferenceId = referenceId;
        }

        public String getSubjectName() {
            return SubjectName;
        }

        public void setSubjectName(String studentName) {
            SubjectName = studentName;
        }

        public String getCredits() {
            return Credits;
        }

        public void setCredits(String credits) {
            Credits = credits;
        }

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String grade) {
            Grade = grade;
        }

        public String getResult() {
            return Result;
        }

        public void setResult(String result) {
            Result = result;
        }

    }
}
