package io.smallbird.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class Base64ImgUtil {

    public static void main(String[] args) {
        String strImg = GetImageStr("./error.jpg");
        System.out.println(strImg);
        System.out.println(strImg.length());
//        GenerateImage(strImg, "./2-1111.jpg");

//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("token", "334b0c4c-a186-42ab-9bf8-24e622e771fc");
//
//        HttpMethod requestMethod = HttpMethod.POST;
//
//        org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity("{\"pageNum\":0,\"pageSize\":10}", headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
////                "https://p.9ishell.com/xiaochengxu/wx/cySchool/list",
//                "http://localhost:8080/xiaochengxu/wx/cySchool/list",
//                requestMethod, entity, String.class);
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            Map jsonMap = JSON.parseObject(responseEntity.getBody(), Map.class);
//            List<Map<String,Object>> list = (List<Map<String, Object>>) jsonMap.get("list");
////            for (Map<String, Object> voMap : list) {
////                GenerateImage((String) voMap.get("coverImageData"), "./" + voMap.get("id") + ".jpg");
////            }
//            System.out.println(responseEntity.getBody());
//        }
    }

    /**
     * 图片转化成base64字符串
     *
     * @param imgFile - 转换的图片路径
     * @return imgStr --图片转换后的二进制字节
     */
    public static String GetImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(in);
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param imgStr - 图片二进制字节 imgFilePath-新生成的图片路径
     * @return
     */
    public static boolean GenerateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) {
            return false;// 图像数据为空
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            CloseUtil.close(out);
        }
    }
}