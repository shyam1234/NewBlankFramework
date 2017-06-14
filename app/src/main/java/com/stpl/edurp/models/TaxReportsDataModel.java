package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 27-05-2017.
 */

public class TaxReportsDataModel extends IModel {
    /*{
            "MessageResult": "OK",
            "MessageBody": [
                {
                    "UniversityId": 3,
                    "FinancialYearId": 43,
                    "FinancialYearCode": "FY201718"
                },
                {
                    "UniversityId": 3,
                    "FinancialYearId": 44,
                    "FinancialYearCode": "FY201213"
                },
                {
                    "UniversityId": 3,
                    "FinancialYearId": 45,
                    "FinancialYearCode": "FY201819"
                }
            ]

      }*/

    @SerializedName("MessageResult")
    private String MessageResult;

    @SerializedName("MessageBody")
    private ArrayList<TaxReportsArray> MessageBody = new ArrayList<>();

    public String getMessageResult() {
        return MessageResult;
    }

    public ArrayList<TaxReportsArray> getMessageBody() {
        return MessageBody;
    }

    public class TaxReportsArray {
        @SerializedName("UniversityId")
        private int UniversityId;

        @SerializedName("FinancialYearId")
        private int FinancialYearId;

        @SerializedName("FinancialYearCode")
        private String FinancialYearCode;


        public int getUniversityId() {
            return UniversityId;
        }

        public int getFinancialYearId() {
            return FinancialYearId;
        }

        public String getFinancialYearCode() {
            return FinancialYearCode;
        }
    }
}
