package com.jobapps.governmentjobnews.Model;

public class CgpaModel {

    private final String subName;
    private final String subGrade;
    private final String subCredit;
    private final String subTotal;

    public CgpaModel(String subName, String subGrade, String subCredit, String subTotal) {
        this.subName = subName;
        this.subGrade = subGrade;
        this.subCredit = subCredit;
        this.subTotal = subTotal;
    }

    public String getSubName() {
        return subName;
    }

    public String getSubGrade() {
        return subGrade;
    }

    public String getSubCredit() {
        return subCredit;
    }

    public String getSubTotal() {
        return subTotal;
    }

}
