package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 27-05-2017.
 */

public class PayslipDataModel  extends IModel {
    /*{
        "MessageResult":"OK",
        "MessageBody":[
            {
            "FinancialYearMonthId":457,
            "FinancialYearId":43,
            "EmployeePayrollId":15235,
            "EmployeeNumber":"EMP0319",
            "EmployeeName":"DivyaEmp1",
            "Gender":"Female",
            "Department":"FAI",
            "month":"July 2017",
            "DateOfJoining":"01.Apr.2012",
            "DownloadPayslip":"Download Payslip",
            "EmployeeId":319,
            "Status":"Not Paid",
            "MonthSort":"2017-07-01T00:00:00"
            }]
      }*/

    @SerializedName("MessageResult")
    private String MessageResult;

    @SerializedName("MessageBody")
    private ArrayList<PayslipArray> MessageBody = new ArrayList<>();

    public String getMessageResult() {
        return MessageResult;
    }

    public ArrayList<PayslipArray> getMessageBody() {
        return MessageBody;
    }

    public class PayslipArray {
        @SerializedName("FinancialYearMonthId")
        private int FinancialYearMonthId;

        @SerializedName("FinancialYearId")
        private int FinancialYearId;

        @SerializedName("EmployeePayrollId")
        private int EmployeePayrollId;

        @SerializedName("EmployeeNumber")
        private String EmployeeNumber;

        @SerializedName("EmployeeName")
        private String EmployeeName;

        @SerializedName("Gender")
        private String Gender;

        @SerializedName("Department")
        private String Department;

        @SerializedName("month")
        private String month;

        @SerializedName("DateOfJoining")
        private String DateOfJoining;

        @SerializedName("DownloadPayslip")
        private String DownloadPayslip;

        @SerializedName("EmployeeId")
        private int EmployeeId;

        @SerializedName("Status")
        private String Status;

        @SerializedName("MonthSort")
        private String MonthSort;

        public int getFinancialYearMonthId() {
            return FinancialYearMonthId;
        }

        public int getFinancialYearId() {
            return FinancialYearId;
        }

        public int getEmployeePayrollId() {
            return EmployeePayrollId;
        }

        public String getEmployeeNumber() {
            return EmployeeNumber;
        }

        public String getGender() {
            return Gender;
        }

        public String getEmployeeName() {
            return EmployeeName;
        }

        public String getDepartment() {
            return Department;
        }

        public String getMonth() {
            return month;
        }

        public String getDateOfJoining() {
            return DateOfJoining;
        }

        public String getDownloadPayslip() {
            return DownloadPayslip;
        }

        public int getEmployeeId() {
            return EmployeeId;
        }

        public String getStatus() {
            return Status;
        }

        public String getMonthSort() {
            return MonthSort;
        }
    }
}
