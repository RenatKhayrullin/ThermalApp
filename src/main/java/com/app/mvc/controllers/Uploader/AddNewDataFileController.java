package com.app.mvc.controllers.Uploader;


import com.app.mvc.controllers.FormClasses.UploadFile;
import com.app.mvc.applicationServices.FileProcessingService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AddNewDataFileController
{
    @Autowired
    FileProcessingService fileProcessingService;

    @RequestMapping(value = "/uploadNewDataSet", method = RequestMethod.GET)
    public String getDataFile(Model uiModel)
    {
        uiModel.addAttribute("uploadFile", new UploadFile());
        return "UploadDataFile";
    }

    @RequestMapping(value = "/uploadSuccess", method = RequestMethod.POST)
    public String uploadDataFile(@ModelAttribute UploadFile uploadFile, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "redirect:uploadNewDataSet";
        }

        if(uploadFile.getUploadFile() != null)
        {
            try
            {
                validateFile(uploadFile.getUploadFile());

                fileProcessingService.SaveDataFile(uploadFile, uploadFile.getUploadFile().getOriginalFilename());

            }
            catch (FileUploadException e)
            {
                System.out.println(e.getMessage());
                bindingResult.reject(e.getMessage());
                return "redirect:uploadNewDataSet";
            }
        }
        else
        {
            return "redirect:uploadNewDataSet";
        }

        return "UploadSuccess";
    }

    private void validateFile(MultipartFile file) throws FileUploadException
    {
        if(!(file.getOriginalFilename().endsWith("xls") || file.getOriginalFilename().endsWith("xlsx")
                || file.getOriginalFilename().endsWith("csv")))
            throw new FileUploadException("Only XLS, XLSX, CSV formats accepted");
    }
}
