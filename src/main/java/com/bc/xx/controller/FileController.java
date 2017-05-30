package com.bc.xx.controller;

import com.bc.xx.service.DownloadUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * FileController
 *
 * @author xiaobc
 * @date 17/5/30
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    private static final String IMG_PATH = "/opt/data/xx_img";

    @Autowired
    private DownloadUploadService downloadUploadService;


    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public void uploadImg2(HttpServletRequest req){

        MultiValueMap<String, MultipartFile> multiFileMap = ((StandardMultipartHttpServletRequest) req).getMultiFileMap();

        if(multiFileMap!=null && multiFileMap.containsKey("img")){
            MultipartFile file = multiFileMap.getFirst("img");
            String url = downloadUploadService.saveFile(IMG_PATH, file);
            //TODO 将返回的文件名存到数据库
        }

        return;
    }

}
