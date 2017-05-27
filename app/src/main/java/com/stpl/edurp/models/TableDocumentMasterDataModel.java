package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 28-01-2017.
 */

public class TableDocumentMasterDataModel implements Serializable{
    @SerializedName("ReferenceId")
    private  String referenceid;
    @SerializedName("MenuCode")
    private  String menucode;
    @SerializedName("DocumentName")
    private  String documentname;
    @SerializedName("DocumentPath")
    private  String documentpath;
    @SerializedName("DocumentExtn")
    private  String documentextn;
    @SerializedName("IsAttachment")
    private  String isattachment;
    @SerializedName("MediaType")
    private  String mediatype;
    @SerializedName("SortOrder")
    private  String sortorder;
    @SerializedName("DocumentId")
    private  String documentId;
    @SerializedName("DocumentMasterId")
    private  int DocumentMasterId;
    @SerializedName("FileURL")
    private  String FileURL;


    public String getReferenceid() {
        return referenceid;
    }
    public void setReferenceid(String referenceid) {
        this.referenceid = referenceid;
    }
    public String getMenucode() {
        return menucode;
    }
    public void setMenucode(String menucode) {
        this.menucode = menucode;
    }
    public String getDocumentname() {
        return documentname;
    }
    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }
    public String getDocumentpath() {
        return documentpath;
    }
    public void setDocumentpath(String documentpath) {
        this.documentpath = documentpath;
    }
    public String getDocumentextn() {
        return documentextn;
    }
    public void setDocumentextn(String documentextn) {
        this.documentextn = documentextn;
    }
    public String getIsattachment() {
        return isattachment;
    }
    public void setIsattachment(String isattachment) {
        this.isattachment = isattachment;
    }
    public String getMediatype() {
        return mediatype;
    }
    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }
    public String getSortorder() {
        return sortorder;
    }
    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public int getDocumentMasterId() {
        return DocumentMasterId;
    }
    public void setDocumentMasterId(int documentMasterId) {
        DocumentMasterId = documentMasterId;
    }

    public String getFileURL() {
        return FileURL;
    }

    public void setFileURL(String fileURL) {
        FileURL = fileURL;
    }
}
