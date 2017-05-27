package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 02-03-2017.
 */

public class NewsDetailsCommentLikeDataModel extends IModel {

    @SerializedName("LikeMaster")
    private ArrayList<LikeMasterDataModel> LikeMaster = new ArrayList<LikeMasterDataModel>();
    @SerializedName("CommentMaster")
    private ArrayList<CommentMasterDataModel> CommentMaster = new ArrayList<CommentMasterDataModel>();

    public ArrayList<LikeMasterDataModel> getLikeMaster() {
        return LikeMaster;
    }

    public ArrayList<CommentMasterDataModel> getCommentMaster() {
        return CommentMaster;
    }

    public class LikeMasterDataModel {
        /*"LikedOn":"28-Jan-2017",
           "LikedBy":"Admin1",
           "UserType":"O"*/
        @SerializedName("LikedOn")
        private String LikedOn;
        @SerializedName("LikedBy")
        private String LikedBy;
        @SerializedName("UserType")
        private String UserType;

        public String getLikedOn() {
            return LikedOn;
        }

        public void setLikedOn(String likedOn) {
            LikedOn = likedOn;
        }

        public String getLikedBy() {
            return LikedBy;
        }

        public void setLikedBy(String likedBy) {
            LikedBy = likedBy;
        }

        public String getUserType() {
            return UserType;
        }

        public void setUserType(String userType) {
            UserType = userType;
        }
    }

    public class CommentMasterDataModel {
        /*{
        "Comment":"HelloNowYes",
        "CommentedOn":"05-Feb-2017",
        "CommentedBy":"Parent 1",
        "UserType":"P",
        "CommentId":16
        },*/
        private String Comment;
        private String CommentedOn;
        private String CommentedBy;
        private String UserType;
        private int CommentId;

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getCommentedOn() {
            return CommentedOn;
        }

        public void setCommentedOn(String commentedOn) {
            CommentedOn = commentedOn;
        }

        public String getCommentedBy() {
            return CommentedBy;
        }

        public void setCommentedBy(String commentedBy) {
            CommentedBy = commentedBy;
        }

        public String getUserType() {
            return UserType;
        }

        public void setUserType(String userType) {
            UserType = userType;
        }

        public int getCommentId() {
            return CommentId;
        }

        public void setCommentId(int commentId) {
            CommentId = commentId;
        }
    }
}
