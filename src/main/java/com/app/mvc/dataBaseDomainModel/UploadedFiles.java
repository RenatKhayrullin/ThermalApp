package com.app.mvc.dataBaseDomainModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(schema = "ont", name = "uploaded_files")
public class UploadedFiles implements Serializable
{
    private Long id;
    private String fileName;
    private String uploadState; //checked, nonchecked, uploaded
    @Temporal(TemporalType.DATE)
    private Date uploadDate;
    private Boolean needToRefactor;

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    @Column(name = "file_name", nullable = false, length = 100)
    public String getFileName() {return fileName;}

    public void setFileName(String fileName) {this.fileName = fileName;}

    @Column(name = "upload_state", nullable = false, length = 10)
    public String getUploadState() {
        return uploadState;
    }

    public void setUploadState(String uploadState) {
        this.uploadState = uploadState;
    }

    @Column(name = "upload_date", nullable = false)
    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Column(name = "need_to_refact", nullable = false)
    public Boolean getNeedToRefactor() {
        return needToRefactor;
    }

    public void setNeedToRefactor(Boolean needToRefactor) {
        this.needToRefactor = needToRefactor;
    }

    /*
    @PrePersist
    protected void onCreate()
    {
        int day = LocalDateTime.now().getDayOfMonth();
        int month = LocalDateTime.now().getMonthValue();
        int year = LocalDateTime.now().getYear();
        needToRefactor = false;
        uploadDate = new Date(year, month, day);
        uploadState = "nonchecked";
    }
    */
}