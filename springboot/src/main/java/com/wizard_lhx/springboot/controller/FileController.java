package com.wizard_lhx.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.wizard_lhx.springboot.common.AuthAccess;
import com.wizard_lhx.springboot.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/18 17:10
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private final static String ROOT_PATH = System.getProperty("user.dir")+"\\files";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String mainName = FileUtil.mainName(fileName);
        String extName = FileUtil.extName(fileName);

        if(!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);
        }
        if(FileUtil.exist(ROOT_PATH+File.separator+fileName)){
            fileName = System.currentTimeMillis()+"_"+mainName+"."+extName;
        }
        File saveFile = new File(ROOT_PATH+File.separator+fileName);
        file.transferTo(saveFile);
        String url = "http://"+ip+":"+port+"/files/download/"+fileName;
        return Result.success(url);
    }

    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        //response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));//文件下载
        //response.addHeader("Content-Disposition", "inline;filename="+ URLEncoder.encode(fileName,"UTF-8"));//文件预览
        String filePath = ROOT_PATH+File.separator+fileName;
        if(!FileUtil.exist(filePath)){
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes); //文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }
}
