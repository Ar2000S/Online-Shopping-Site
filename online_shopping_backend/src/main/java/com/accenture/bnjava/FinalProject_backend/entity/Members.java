package com.accenture.bnjava.FinalProject_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ONLINE_MEMBER")
public class Members {

    @Id
    @Column(name = "MEMBER_NO")
    @NotNull(message = "Member number cannot be empty")
    private Integer memberNo;

    @Column(name = "NAME")
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 20, message = "Name must be at most 20 characters")
    private String userName;

    @Column(name = "PASSWORD")
    @NotBlank(message = "Password cannot be empty")
    @Size(max = 8, message = "Password must be at most 8 characters")
    private String pwd;

    @Column(name = "AGE")
    @NotNull(message = "Age cannot be empty. This is for legal reasons.")
    private Integer age;

    @Column(name = "SEX")
    private String sex;   // char(1) — validate allowed values later if needed

    @Column(name = "ZIP")
    @NotBlank(message = "Zip code cannot be empty.")
    @Size(max = 8, message = "Zip code must be at most 8 characters")
    private String zip;

    @Column(name = "ADDRESS")
    @NotBlank(message = "Address cannot be empty.")
    @Size(max = 50, message = "Address must be at most 50 characters")
    private String addr;

    @Column(name = "TEL")
    @NotBlank(message = "Phone number is needed for user creation")
    @Size(max = 20, message = "Phone number must be at most 20 characters")
    private String tel;

    @Column(name = "REGISTER_DATE")
    @NotNull(message = "Registration date is required")
    private LocalDate regDate;

    @Column(name = "DELETE_FLG")
    private String deleteFlag;   // is a varchar on the db, add some validation

    @Column(name = "LAST_UPD_DATE")
    private LocalDateTime lastUpd;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDate.now();
        this.lastUpd = LocalDateTime.now();
        if (this.deleteFlag == null) {
            this.deleteFlag = "0";   // 0 needs to be the default value
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpd = LocalDateTime.now();
    }

    // constructors, getters, setters
    public Members(){}

    public Members(Integer memberNo,
                   String userName,
                   String pwd,
                   Integer age,
                   String sex,
                   String zip,
                   String addr,
                   String tel,
                   LocalDate regDate,
                   String deleteFlag,
                   LocalDateTime lastUpd){
        this.memberNo = memberNo;
        this.userName = userName;
        this.pwd = pwd;
        this.age = age;
        this.sex = sex;
        this.zip = zip;
        this.addr = addr;
        this.tel = tel;
        this.regDate = regDate;
        this.deleteFlag = deleteFlag;
        this.lastUpd = lastUpd;
    }

    // Getters
    public Integer getMemberNo() { return memberNo; }
    public String getUserName() { return userName; }
    public String getPwd() { return pwd; }
    public Integer getAge() { return age; }
    public String getSex() { return sex; }
    public String getZip() { return zip; }
    public String getAddr() { return addr; }
    public String getTel() { return tel; }
    public LocalDate getRegDate() { return regDate; }
    public String getDeleteFlag() { return deleteFlag; }
    public LocalDateTime getLastUpd() { return lastUpd; }

    // Setters
    public void setMemberNo(Integer memberNo) { this.memberNo = memberNo; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public void setAge(Integer age) { this.age = age; }
    public void setSex(String sex) { this.sex = sex; }
    public void setZip(String zip) { this.zip = zip; }
    public void setAddr(String addr) { this.addr = addr; }
    public void setTel(String tel) { this.tel = tel; }
    public void setRegDate(LocalDate regDate) { this.regDate = regDate; }
    public void setDeleteFlag(String deleteFlag) { this.deleteFlag = deleteFlag; }
    public void setLastUpd(LocalDateTime lastUpd) { this.lastUpd = lastUpd; }






}