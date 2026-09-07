package com.accenture.bnjava.FinalProject_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ONLINE_STAFF")
public class Staff {

    @Id
    @Column(name = "STAFF_NO")
    @NotNull(message = "Staff number cannot be empty")
    private Integer staffNo;

    @Column(name = "NAME")
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 20, message = "Name must be at most 20 characters")
    private String staffName;

    @Column(name = "PASSWORD")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 8, message = "Password must be exactly 8 characters")
    private String pwd;

    @Column(name = "AGE")
    @NotNull(message = "Age cannot be empty. This is for legal reasons.")
    private Integer age;

    @Column(name = "SEX")
    private String sex;   // char(1). validate allowed values later if needed

    @Column(name = "REGISTER_DATE")
    @NotNull(message = "Registration date is required")
    private LocalDate regDate;

    @Column(name = "LAST_UPD_DATE")
    private LocalDateTime lastUpd;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDate.now();
        this.lastUpd = LocalDateTime.now();
        }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpd = LocalDateTime.now();
    }

    //Constructors

    public Staff(){}

    public Staff(Integer staffNo,
                 String staffName,
                 String pwd,
                 Integer age,
                 String sex,
                 LocalDate regDate,
                 LocalDateTime lastUpd){
        this.staffName = staffName;
        this.staffNo = staffNo;
        this.pwd = pwd;
        this.age = age;
        this.sex = sex;
        this.regDate = regDate;
        this.lastUpd = lastUpd;
    }

    // Getters
    public Integer getStaffNo() { return staffNo; }
    public String getStaffName() { return staffName; }
    public String getPwd() { return pwd; }
    public Integer getAge() { return age; }
    public String getSex() { return sex; }
    public LocalDate getRegDate() { return regDate; }
    public LocalDateTime getLastUpd() { return lastUpd; }

    // Setters
    public void setStaffNo(Integer staffNo) { this.staffNo = staffNo; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public void setAge(Integer age) { this.age = age; }
    public void setSex(String sex) { this.sex = sex; }
    public void setRegDate(LocalDate regDate) { this.regDate = regDate; }
    public void setLastUpd(LocalDateTime lastUpd) { this.lastUpd = lastUpd; }


}
