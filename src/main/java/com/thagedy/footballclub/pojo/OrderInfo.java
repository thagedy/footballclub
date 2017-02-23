package com.thagedy.footballclub.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private Integer id;

    private String orderNo;

    private String wxAppid;

    private String studenName;

    private String studentSchool;

    private String studentClass;

    private String parentName;

    private String phoneNumber;

    private Byte courseType;

    private BigDecimal payFee;

    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid == null ? null : wxAppid.trim();
    }

    public String getStudenName() {
        return studenName;
    }

    public void setStudenName(String studenName) {
        this.studenName = studenName == null ? null : studenName.trim();
    }

    public String getStudentSchool() {
        return studentSchool;
    }

    public void setStudentSchool(String studentSchool) {
        this.studentSchool = studentSchool == null ? null : studentSchool.trim();
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass == null ? null : studentClass.trim();
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Byte getCourseType() {
        return courseType;
    }

    public void setCourseType(Byte courseType) {
        this.courseType = courseType;
    }

    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}