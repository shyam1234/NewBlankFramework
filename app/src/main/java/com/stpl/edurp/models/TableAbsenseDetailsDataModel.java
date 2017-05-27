package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 31-03-2017.
 */

public class TableAbsenseDetailsDataModel extends IModel {
    @SerializedName("MessageResult")
    private String MessageResult;
    @SerializedName("MessageBody")
    private ArrayList<MessageBodyDataModel> MessageBody = new ArrayList<>();


    public String getMessageResult() {
        return MessageResult;
    }

    public ArrayList<MessageBodyDataModel> getMessageBody() {
        return MessageBody;
    }


    public static class MessageBodyDataModel {
        /* {
      "ReferenceId": 2427,
      "SubjectId": 1747,
      "AbsenceDate": "20160401"
    }*/
        @SerializedName("ReferenceId")
        private int ReferenceId;
        @SerializedName("SubjectId")
        private String SubjectId;
        @SerializedName("AbsenceDate")
        private String AbsenceDate;

        public int getReferenceId() {
            return ReferenceId;
        }

        public void setReferenceId(int referenceId) {
            ReferenceId = referenceId;
        }

        public String getSubjectId() {
            return SubjectId;
        }

        public void setSubjectId(String subjectId) {
            SubjectId = subjectId;
        }

        public String getAbsenceDate() {
            return AbsenceDate;
        }

        public void setAbsenceDate(String absenceDate) {
            AbsenceDate = absenceDate;
        }
    }
}
