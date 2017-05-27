package com.stpl.edurp.models;

/**
 * Created by Admin on 21-01-2017.
 */

public class TableLanguageDataModel {

    private int UniversityId;
    private int ConversionId;
    private String ConversionCode;
    private String EnglishVersion;
    private String BahasaVersion;
    private String DateModified;

    public int getUniversityId() {
        return UniversityId;
    }

    public void setUniversityId(int universityId) {
        UniversityId = universityId;
    }

    public int getConversionId() {
        return ConversionId;
    }

    public void setConversionId(int conversionId) {
        ConversionId = conversionId;
    }

    public String getConversionCode() {
        return ConversionCode;
    }

    public void setConversionCode(String conversionCode) {
        ConversionCode = conversionCode;
    }

    public String getEnglishVersion() {
        return EnglishVersion;
    }

    public void setEnglishVersion(String englishVersion) {
        EnglishVersion = englishVersion;
    }

    public String getBahasaVersion() {
        return BahasaVersion;
    }

    public void setBahasaVersion(String bahasaVersion) {
        BahasaVersion = bahasaVersion;
    }

    public String getDateModified() {
        return DateModified;
    }

    public void setDateModified(String dateModified) {
        DateModified = dateModified;
    }
}
