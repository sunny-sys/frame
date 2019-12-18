package io.smallbird.common.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @Description  采用webservice调用中国气象局的天气预报
 * @Author xupj
 * @Date 23:58 2019/8/26
 **/
public class WeatherUtil {

    /**
     * 对服务器端返回的XML文件流进行解析
     *
     * @param city 用户输入的城市名称
     * @return 字符串 用#分割
     */
    public static String getWeather(String city) {
        InputStream is = null;
        try {
            //使用Dom解析
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            //获取调用接口后返回的流
            is = getSoapInputStream(city);
            if (Objects.isNull(is)) return null;
            doc = db.parse(is);
            //xml的元素标签是"<string>值1</string><string>值2</string>……"
            NodeList nl = doc.getElementsByTagName("string");
            StringBuffer sb = new StringBuffer();
            for (int count = 0; count < nl.getLength(); count++) {
                Node n = nl.item(count);
                if (n.getFirstChild().getNodeValue().equals("查询结果为空！")) {
                    sb = new StringBuffer("#");
                    break;
                }
                //解析并以"#"为分隔符,拼接返回结果
                sb.append(n.getFirstChild().getNodeValue()).append("#");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.close(is);
        }
    }

    /*
     * 用户把SOAP请求发送给服务器端，并返回服务器点返回的输入流
     *
     * @param city  用户输入的城市名称
     * @return 服务器端返回的输入流，供客户端读取
     * @throws Exception
     * @备注：有四种请求头格式1、SOAP 1.1；  2、SOAP 1.2 ； 3、HTTP GET； 4、HTTP POST
     * 参考---》http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?op=getWeatherbyCityName
     */
    private static InputStream getSoapInputStream(String city) throws Exception {
        String soap = getSoapRequest(city);
        URL url = new URL("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl");
        URLConnection conn = url.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty(HttpHeaders.CONTENT_LENGTH, Integer.toString(soap.length()));
        conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE + "; charset=" + StandardCharsets.UTF_8);
        conn.setRequestProperty("SOAPAction", "http://WebXml.com.cn/getWeatherbyCityName");
        try (OutputStream os = conn.getOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            osw.write(soap);
            osw.flush();
            return conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 获取SOAP的请求头，并替换其中的标志符号为用户输入的城市
     *
     * @param city： 用户输入的城市名称
     * @return              客户将要发送给服务器的SOAP请求规范
     * @备注：有四种请求头格式1、SOAP 1.1；  2、SOAP 1.2 ； 3、HTTP GET； 4、HTTP POST
     * 参考---》http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?op=getWeatherbyCityName
     * 本文采用：SOAP 1.1格式
     */
    private static String getSoapRequest(String city) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body><getWeatherbyCityName xmlns=\"http://WebXml.com.cn/\">"
                + "<theCityName>"
                + city
                + "</theCityName></getWeatherbyCityName>"
                + "</soap:Body></soap:Envelope>");
        return sb.toString();
    }

    public static void main(String[] args) {
        //查看城市：济南
        String weather = WeatherUtil.getWeather("南京");
        String[] len = weather.split("#");
        for (int i = 0; i < len.length - 1; i++) {
            System.out.println(len[i]);
        }
    }
}