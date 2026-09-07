package com.accenture.bnjava.FinalProject_backend.dto;

import jakarta.validation.constraints.*;

// Carries fields for updating an EXISTING member's own info.
// Password is optional here — leave blank to keep it unchanged.
public class MemberUpdateDto {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 20, message = "Name must be at most 20 characters")
    private String userName;

    @Size(max = 8, message = "Password must be at most 8 characters")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Password must be half-width alphanumeric characters")
    private String pwd;   // optional — null/blank means "don't change"

    @NotNull(message = "Age cannot be empty")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String sex;

    @NotBlank(message = "Zip code cannot be empty")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "Zip code must be in the format NNN-NNNN")
    private String zip;

    @NotBlank(message = "Address cannot be empty")
    @Size(max = 50, message = "Address must be at most 50 characters")
    private String addr;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9-]*$", message = "Phone number must be half-width digits and hyphens")
    @Size(max = 20, message = "Phone number must be at most 20 characters")
    private String tel;

    public MemberUpdateDto() {}

    public String getUserName() { return userName; }
    public String getPwd() { return pwd; }
    public Integer getAge() { return age; }
    public String getSex() { return sex; }
    public String getZip() { return zip; }
    public String getAddr() { return addr; }
    public String getTel() { return tel; }

    public void setUserName(String userName) { this.userName = userName; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public void setAge(Integer age) { this.age = age; }
    public void setSex(String sex) { this.sex = sex; }
    public void setZip(String zip) { this.zip = zip; }
    public void setAddr(String addr) { this.addr = addr; }
    public void setTel(String tel) { this.tel = tel; }
}