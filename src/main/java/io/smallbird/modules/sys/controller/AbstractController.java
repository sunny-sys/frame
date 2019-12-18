package io.smallbird.modules.sys.controller;

import io.smallbird.common.utils.Constant;
import io.smallbird.common.utils.SpringContextUtils;
import io.smallbird.common.utils.Util;
import io.smallbird.modules.sys.entity.SysFileEntity;
import io.smallbird.modules.sys.entity.SysUserEntity;
import io.smallbird.modules.sys.service.SysFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Controller公共组件
 */
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

    protected Long getDeptId() {
        return getUser().getDeptId();
    }

    private static Map<String, String> imageContentType = new HashMap<>();

    static {
        imageContentType.put("jpg", "image/jpeg");
        imageContentType.put("jpeg", "image/jpeg");
        imageContentType.put("png", "image/png");
        imageContentType.put("tif", "image/tiff");
        imageContentType.put("tiff", "image/tiff");
        imageContentType.put("ico", "image/x-icon");
        imageContentType.put("bmp", "image/bmp");
        imageContentType.put("gif", "image/gif");
    }

    /**
     * 上传文件
     *
     * @param request
     * @return
     * @throws IOException
     */
    protected List<SysFileEntity> uploadFile(HttpServletRequest request,String uploadRootPath,String uploadImgPath) throws Exception {
        List<SysFileEntity> files = new ArrayList<>();
        SysFileService sysFileService = SpringContextUtils.getBean(SysFileService.class);
        long startTime = System.currentTimeMillis();
        MultipartResolver multipartResolver = SpringContextUtils.getBean(MultipartResolver.class);
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile multipartFile = multiRequest.getFile(iter.next().toString());
                SysFileEntity sysFileEntity = upload(multipartFile, uploadRootPath,uploadImgPath);
                if (Util.isEmpty(sysFileEntity)) {
                    continue;
                }
                sysFileService.save(sysFileEntity);
                files.add(sysFileEntity);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("File upload time-consuming ：" + (endTime - startTime) + " ms");

        return files;
    }

    public static SysFileEntity upload(MultipartFile multipartFile, String uploadRootPath,String uploadImgPath) throws Exception {
        if (Util.isEmpty(multipartFile)) return null;
        String newFileName = Util.uuid() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String dirPath = uploadRootPath + uploadImgPath;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //上传
        multipartFile.transferTo(new File(dirPath, newFileName));

        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setRelativePath(uploadImgPath + newFileName);
        sysFileEntity.setOriginalName(multipartFile.getOriginalFilename());
        sysFileEntity.setNewFileName(newFileName);
        sysFileEntity.setFileSize(multipartFile.getSize());
        sysFileEntity.setContentType(multipartFile.getContentType());
        sysFileEntity.setCreateTime(new Date());
        return sysFileEntity;
    }

    /**
     * 图片下载/展示
     *
     * @param fileId
     * @param response
     * @throws IOException
     */
    protected void downloadImage(String fileId, HttpServletResponse response,String uploadRootPath) throws IOException {
        SysFileService sysFileService = SpringContextUtils.getBean(SysFileService.class);
        SysFileEntity file = sysFileService.getById(fileId);
        if (file != null) {
            File imgFile = new File(uploadRootPath + file.getRelativePath());
            if (imgFile.exists() && !imgFile.isDirectory()) {
                FileInputStream inputStream = new FileInputStream(imgFile);
                int length = inputStream.available();
                byte data[] = new byte[length];
                response.setContentLength(length);
                String fileName = imgFile.getName();
                String fileType = FilenameUtils.getExtension(fileName).toLowerCase();
                response.setContentType(imageContentType.get(fileType));
                inputStream.read(data);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
            }
        }
    }

    protected void download(String fileId, HttpServletResponse response, HttpServletRequest request,String uploadRootPath) {
        SysFileService sysFileService = SpringContextUtils.getBean(SysFileService.class);
        SysFileEntity file = sysFileService.getById(fileId);
        if (file != null) {
            File imgFile = new File(uploadRootPath + file.getRelativePath());
            if (imgFile.exists() && !imgFile.isDirectory()) {
                try (FileInputStream inputStream = new FileInputStream(imgFile)) {
                    request.setCharacterEncoding(StandardCharsets.UTF_8.name());//确保请求的编码类型为UTF-8,不然文件下载后有可能因为类型不一致出现乱码
                    int length = inputStream.available();
                    byte data[] = new byte[length];
                    response.setContentLength(length);
                    //3.设置响应头文件内容,文件类型、弹出下载对话框、文件大小
                    response.setContentType("application/x-msdownload");
                    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                            + new String(file.getOriginalName().getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()));
                    inputStream.read(data);
                    OutputStream outputStream = response.getOutputStream();
                    outputStream.write(data);
                    outputStream.flush();
                    System.out.println("下载成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断客户端浏览器
     */
    private static String getBrowser(HttpServletRequest request) {
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent != null) {
            if (UserAgent.contains("msie"))
                return "IE";
            if (UserAgent.contains("firefox"))
                return "FF";
            if (UserAgent.contains("safari"))
                return "SF";
        }
        return null;
    }
}
