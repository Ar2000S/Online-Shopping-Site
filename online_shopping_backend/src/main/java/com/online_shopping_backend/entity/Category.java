package com.online_shopping_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ONLINE_CATEGORY")
public class Category {

    @Id
    @Column(name = "CTGR_ID")
    @NotNull(message = "Category ID cannot be empty")
    private Integer ctgrId;

    @Column(name = "NAME")
    @NotBlank(message = "Category name cannot be empty")
    @Size(max = 20, message = "Category name must be at most 20 characters")
    private String name;

    @Column(name = "LAST_UPD_DATE")
    private LocalDateTime lastUpd;

    @PrePersist
    protected void onCreate() {
        this.lastUpd = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpd = LocalDateTime.now();
    }

    public Category() {}

    public Category(Integer ctgrId, String name, LocalDateTime lastUpd) {
        this.ctgrId = ctgrId;
        this.name = name;
        this.lastUpd = lastUpd;
    }

    public Integer getCtgrId() { return ctgrId; }
    public String getName() { return name; }
    public LocalDateTime getLastUpd() { return lastUpd; }

    public void setCtgrId(Integer ctgrId) { this.ctgrId = ctgrId; }
    public void setName(String name) { this.name = name; }
    public void setLastUpd(LocalDateTime lastUpd) { this.lastUpd = lastUpd; }
}