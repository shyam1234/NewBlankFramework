package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.utils.AppLog;

import java.io.Serializable;

/**
 * Created by Admin on 28-01-2017.
 */

public class TableNewsMasterDataModel implements Serializable, Comparable<TableNewsMasterDataModel> {


    /*{
        "MenuCode":"NEW",
        "ParentId":239,
        "StudentId":328,
        "ReferenceId":2,
        "DocumentMasterId":51,
        "DocumentId":52,
        "FilePath":"test.jpg",
        "ReferenceTitle":"test1",
        "ShortBody":"Republic Day honors the date on which th",
        "ThumbnailPath":"https://edurpstorage.blob.core.windows.net/edurpcontainer/DEV/1/51/52?sv=2015-12-11&sr=b&sig=e0UsUhIsBUzvBVo5X%2F6XU4tkYsYdm3kyPA4Gz8jaGCM%3D&se=2017-03-05T07%3A53%3A51Z&sp=rwl&rscd=attachment%3B%20filename%3Dtest.jpg",
        "PublishedOn":null,
        "PublishedBy":"Admin",
        "ExpiryDate":"20170309000000",
        "TotalComments":55,
        "TotalLikes":6
        "LikedByMe":1
        },*/


    @SerializedName("ParentId")
    private String ParentId;
    @SerializedName("StudentId")
    private String StudentId;
    @SerializedName("NewsTitle")
    private String NewsTitle;
    @SerializedName("ShortBody")
    private String ShortBody;
    @SerializedName("NewsBody")
    private String NewsBody;
    @SerializedName("ThumbnailPath")
    private String ThumbNailPath;
    @SerializedName("PublishedOn")
    private String PublishedOn;
    @SerializedName("PublishedBy")
    private String PublishedBy;
    @SerializedName("TotalComments")
    private String TotalComments;
    @SerializedName("TotalLikes")
    private String TotalLikes;
    @SerializedName("MenuCode")
    private String MenuCode;
    @SerializedName("ReferenceId")
    private int ReferenceId;
    @SerializedName("DocumentMasterId")
    private int DocumentMasterId;
    @SerializedName("ReferenceTitle")
    private String ReferenceTitle;
    @SerializedName("ExpiryDate")
    private String ExpiryDate;
    @SerializedName("DocumentId")
    private int DocumentId;
    @SerializedName("FilePath")
    private String FilePath;
    @SerializedName("FileType")
    private String FileType;
    @SerializedName("LikedByMe")
    private int LikedByMe;

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }


    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getShortBody() {
        return ShortBody;
    }

    public void setShortBody(String shortBody) {
        ShortBody = shortBody;
    }

    public String getNewsBody() {
        return NewsBody;
    }

    public void setNewsBody(String newsBody) {
        NewsBody = newsBody;
    }

    public String getThumbNailPath() {
        return ThumbNailPath;
    }

    public void setThumbNailPath(String thumbNailPath) {
        ThumbNailPath = thumbNailPath;
    }

    public String getPublishedOn() {
        return PublishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        PublishedOn = publishedOn;
    }

    public String getPublishedBy() {
        return PublishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        PublishedBy = publishedBy;
    }

    public String getTotalComments() {
        return TotalComments;
    }

    public void setTotalComments(String totalComments) {
        TotalComments = totalComments;
    }

    public String getTotalLikes() {
        return TotalLikes;
    }

    public void setTotalLikes(String totalLikes) {
        TotalLikes = totalLikes;
    }

    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String menuCode) {
        MenuCode = menuCode;
    }

    public int getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(int referenceId) {
        ReferenceId = referenceId;
    }

    public int getDocumentMasterId() {
        return DocumentMasterId;
    }

    public void setDocumentMasterId(int documentMasterId) {
        DocumentMasterId = documentMasterId;
    }

    public String getReferenceTitle() {
        return ReferenceTitle;
    }

    public void setReferenceTitle(String referenceTitle) {
        ReferenceTitle = referenceTitle;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public int getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(int documentId) {
        DocumentId = documentId;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    @Override
    public int compareTo(TableNewsMasterDataModel o) {
        try {
            if (o.getPublishedOn() != null && getPublishedOn() != null)
                return getPublishedOn().compareTo(o.getPublishedOn());
        } catch (Exception e) {
            AppLog.errLog("TableNewsMasterDataModel", e.getMessage());
        }
        return 0;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public int getLikedByMe() {
        return LikedByMe;
    }

    public void setLikedByMe(int likedByMe) {
        LikedByMe = likedByMe;
    }
}
