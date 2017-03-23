package com.app.mvc.applicationServices;

import com.app.mvc.controllers.FormClasses.UploadFile;
import org.apache.commons.fileupload.FileUploadException;

public interface FileProcessingService
{
    void SaveDataFile(UploadFile uploadFile, String fileName) throws FileUploadException;

    void DownloadTableInCvsFormat(); //undeclared
    void DownloadTableInXlsFormat(); //undeclared
}
