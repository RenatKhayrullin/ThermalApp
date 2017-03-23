package com.app.mvc.applicationServices.ServiceImpl;

import com.app.mvc.controllers.FormClasses.UploadFile;
import com.app.mvc.applicationServices.FileProcessingService;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

@Service
public class FileProcessingServiceImpl implements FileProcessingService
{

    private ResourceBundle rb = ResourceBundle.getBundle("applicationConfig");

    @Override
    public void SaveDataFile (UploadFile uploadFile, String fileName)
            throws FileUploadException
    {
        try
        {
            File file = new File(rb.getString("fileResourcePath")+fileName);
            FileUtils.writeByteArrayToFile(file, uploadFile.getUploadFile().getBytes());
        }
        catch (IOException e)
        {
            throw new FileUploadException("Unable to save file", e);
        }
    }



    @Override
    public void DownloadTableInCvsFormat() {

    }

    @Override
    public void DownloadTableInXlsFormat() {

    }
}
