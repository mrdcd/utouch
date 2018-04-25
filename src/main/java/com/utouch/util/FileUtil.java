package com.utouch.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/*
* 文件上传工具类
* */
public class FileUtil {

    private static String BASEPATH = "http://140.143.25.150:8080/utouch";


    /**
     * 同步上传多个文件
     *
     * @param files 文件数组
     * @param type 根据文件类型确定的路径信息
     * @param request 获取绝对路径
     * @return 文件url组成的字符串
     */
    public static String uploadFiles(MultipartFile[] files,String type,HttpServletRequest request) {
        StringBuffer urls = new StringBuffer();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (!file.isEmpty()) {
                //上传文件路径
                String path = request.getServletContext().getRealPath("/"+type+"/");
                //上传文件名
                String filename = file.getOriginalFilename();
                File filepath = new File(path, filename);
                //判断路径是否存在，如果不存在就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                //将上传文件保存到一个目标文件当中
                try {
                    file.transferTo(new File(path + File.separator + filename));
                    urls.append(BASEPATH);
                    urls.append("/");
                    urls.append(type);
                    urls.append("/");
                    urls.append(filename);
                    urls.append(";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String res = urls.toString();
        return res;
    }

    /**
     * 上传单个文件
     *
     * @param file 文件
     * @param type 文件类型
     * @param request 获取绝对路径
     * @return 文件url
     */
    public static String uploadFile(MultipartFile file, String type,HttpServletRequest request) {
        StringBuffer url = new StringBuffer();

        if (!file.isEmpty()) {
            //上传文件路径
            String path = request.getServletContext().getRealPath("/"+type+"/");
            //上传文件名
            String filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            try {
                file.transferTo(new File(path + File.separator + filename));
                url.append(BASEPATH);
                url.append("/");
                url.append(type);
                url.append("/");
                url.append(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String res = url.toString();
        return res;
    }
}
