package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;


/**
 * Created by Admin on 31-12-2016.
 */
public class LanguageArrayDataModel extends IModel {
    @SerializedName("LanguageArray")
    public ArrayList<LanguageDataModel> LanguageArray;
    public LanguageArrayDataModel() {
        LanguageArray = new ArrayList<LanguageDataModel>();
    }

    public class LanguageDataModel {
          /*{
          "UniversityId": 1,
          "ConversionId": 1,
          "ConversionCode": "AC",
          "EnglishVersion": "Admin Cancelled",
          "BahasaVersion": "Admin Dibatalkan",
          "DateModified": "2016-11-27T00:00:00"
        }*/

        @SerializedName("UniversityId")
        private int UniversityId;
        @SerializedName("ConversionId")
        private int ConversionId;
        @SerializedName("ConversionCode")
        private String ConversionCode;
        @SerializedName("EnglishVersion")
        private String EnglishVersion;
        @SerializedName("BahasaVersion")
        private String BahasaVersion;
        @SerializedName("DateModified")
        private String DateModified;

        public int getUniversityId() {
            return UniversityId;
        }

        public int getConversionId() {
            return ConversionId;
        }

        public String getConversionCode() {
            return ConversionCode;
        }

        public String getEnglishVersion() {
            return EnglishVersion;
        }

        public String getBahasaVersion() {
            return BahasaVersion;
        }

        public String getDateModified() {
            return DateModified;
        }
    }
}
