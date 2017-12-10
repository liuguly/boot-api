package com.three.sharecare.bootapi.service;

import com.google.common.collect.Lists;
import com.three.sharecare.bootapi.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);


    /**
     * json 工具
     */
    private JsonMapper jsonMapper = new JsonMapper();

    @Value("${file.upload.path}")
    private String uploadPath ;

    /**
     * 获取上传的图片地址
     * @param multipartFiles 多文件实体
     * @return json路径
     */
    public String getPhotoPaths(HttpServletRequest request, MultipartFile[] multipartFiles, String accountId,String fileType){
        List<String> imageResult = Lists.newArrayList();
        //获取当前web根路径
//        String path = request.getSession().getServletContext().getRealPath(File.separator);

        LOGGER.info("当前path:"+uploadPath);

        String currentDate = DateFormatUtils.format(new Date(),"yyyMMddHHmmss");
        //文件磁盘路径
        String imageDirectory = uploadPath.concat(File.separator)
                .concat("static").concat(File.separator)
                .concat("images").concat(File.separator)
                .concat(accountId).concat(File.separator)
                .concat(fileType).concat(File.separator)
                .concat(currentDate);
        //创建上传的磁盘路径
        FileUtils.createDirectory(imageDirectory);
        //文件相对路径
        String imageRelativeDirectory = StringUtils.substring(imageDirectory,imageDirectory.indexOf("static"),imageDirectory.length());
        for(MultipartFile multipartFile : multipartFiles){
            try {
                String fileName = multipartFile.getOriginalFilename();
                String fileNameWithPath = imageDirectory.concat(File.separator).concat(fileName);
                multipartFile.transferTo(new File(fileNameWithPath));
                String fileNameWithRelativePath = imageRelativeDirectory.concat(File.separator).concat(fileName);
                imageResult.add(fileNameWithRelativePath);
            } catch (IOException e) {
                LOGGER.error("上传文件失败 {}", e.getMessage() );
            }
        }
        return jsonMapper.toJson(imageResult);
    }


    /**
     * 获取上传的图片地址
     * @param multipartFile 文件实体
     * @return json路径
     */
    public String getPhotoPath(HttpServletRequest request, MultipartFile multipartFile, String accountId,String fileType){
        return uploadFile(multipartFile,accountId,fileType);
    }


    /**
     * 获取上传的图片地址
     * @param multipartFile 文件实体
     * @return json路径
     */
    public String getPhotoPath(MultipartFile multipartFile, String accountId,String fileType){
        return uploadFile(multipartFile,accountId,fileType);
    }


    private String uploadFile(MultipartFile multipartFile, String accountId,String fileType){
        String currentDate = DateFormatUtils.format(new Date(),"yyyMMddHHmmss");
        //文件磁盘路径
        String imageDirectory = uploadPath.concat(File.separator)
                .concat("static").concat(File.separator)
                .concat("images").concat(File.separator)
                .concat(accountId).concat(File.separator)
                .concat(fileType).concat(File.separator)
                .concat(currentDate);
        //创建上传的磁盘路径
        FileUtils.createDirectory(imageDirectory);
        //文件相对路径
        String imageRelativeDirectory = StringUtils.substring(imageDirectory,imageDirectory.indexOf("static"),imageDirectory.length());
        String fileName = multipartFile.getOriginalFilename();
        String fileNameWithPath = imageDirectory.concat(File.separator).concat(fileName);
        try {
            multipartFile.transferTo(new File(fileNameWithPath));
        } catch (IOException e) {
            LOGGER.error("上传文件失败 {}", e.getMessage() );
        }
        String imageResult = imageRelativeDirectory.concat(File.separator).concat(fileName);
        return imageResult;
    }


}
