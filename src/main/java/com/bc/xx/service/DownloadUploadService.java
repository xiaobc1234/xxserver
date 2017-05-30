package com.bc.xx.service;

import com.bc.xx.utils.RandomGUIDUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * DownloadUploadService
 *
 * @author xiaobc
 * @date 17/5/30
 */
@Service("downloadUploadService")
public class DownloadUploadService {

    public String saveFile(String parent, MultipartFile srcFile) {
//        String fileName = srcFile.getOriginalFilename();
        String fileName = RandomGUIDUtil.getRawGUID()+".png";
        File destFile = new File(parent, fileName);
        try {
            FileUtils.copyInputStreamToFile(srcFile.getInputStream(), destFile);
            return fileName;
        } catch (IOException e) {}
        return null;
    }

}
