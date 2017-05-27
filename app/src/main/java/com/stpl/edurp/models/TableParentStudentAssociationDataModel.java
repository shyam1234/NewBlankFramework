package com.stpl.edurp.models;

/**
 * Created by Admin on 14-01-2017.
 */

public class TableParentStudentAssociationDataModel {
    private boolean is_default;
    private int parent_id;
    private int studentid;

    public boolean getIs_default() {
        return is_default;
    }

    public void setIsDefault(boolean is_default) {
        this.is_default = is_default;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParentId(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }
}
