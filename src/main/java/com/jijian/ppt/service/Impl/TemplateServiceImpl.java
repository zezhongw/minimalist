package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.TemplateFileDetail;
import com.jijian.ppt.service.TemplateService;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 13:59
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;


    @Value("${file.templateDirectory}")
    private static String templateDirectory;

    /**
     * 上传模板文件
     * @param uploadFile
     * @param templateFileDetail
     * @param req
     * @return
     * @throws IOException
     */
    @Override
    public UniversalResponseBody<TemplateFileDetail> uploadTemplateFile(MultipartFile uploadFile,TemplateFileDetail templateFileDetail, HttpServletRequest req) throws IOException {

        String newName = UUID.randomUUID().toString()+".pptx";
        String filePath =templateDirectory+""+ newName;
        templateFileDetail.setFilePath(filePath);
        templateFileDetailMapper.InsertTemplateFileDetail(templateFileDetail);
        //文件的保存操作
        uploadFile.transferTo(new File(templateDirectory,newName));

        return new UniversalResponseBody<TemplateFileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),templateFileDetail);
    }


    @Override
    public UniversalResponseBody<List<TemplateFileDetail>> getTemplateByTag(String templateTag) {
        List<TemplateFileDetail> templateFileDetails = templateFileDetailMapper.GetTemplateDetailByTag(templateTag);
        if (templateFileDetails.isEmpty()){
            return new UniversalResponseBody(ResponseResultEnum.TAG_IS_EMPTY.getCode(),ResponseResultEnum.TAG_IS_EMPTY.getMsg());
        }else{
            return new UniversalResponseBody<List<TemplateFileDetail>>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),templateFileDetails);
        }
    }
}
