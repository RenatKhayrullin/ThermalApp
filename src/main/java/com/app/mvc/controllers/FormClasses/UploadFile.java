package com.app.mvc.controllers.FormClasses;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFile
{
    private CommonsMultipartFile uploadFile;

    public CommonsMultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(CommonsMultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
