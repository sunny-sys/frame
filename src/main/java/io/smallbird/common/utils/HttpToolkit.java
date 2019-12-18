package io.smallbird.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * 基于 httpclient 4.3.3版本的 http工具类
 */
public class HttpToolkit {

    private final static Logger logger = LoggerFactory.getLogger(HttpToolkit.class);

    private static final CloseableHttpClient httpClient;
    public static final String DEFAULTCHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, DEFAULTCHARSET);
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, DEFAULTCHARSET);
    }

    public static String doPostOkr(String url, Map<String, String> params, String json) throws UnsupportedEncodingException {
        return doPostForOkr(url, "post", json, params);
    }

//    public static void main(String[] args) {
//        String result = doGet("http://www.chuangyewa.com/data/companydetail?schoolid=64201d27e4aa496dac38636b3cb5c732&wapageid=2018120412160001", null);
//        Map map = JSON.parseObject(result, Map.class);
//        System.out.println("+++++++++++++++++++++++++");
//        System.out.println(result);
//        System.out.println(map);
//    }

    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error("接口调用失败！", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, DEFAULTCHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error("接口调用失败！", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP Post 同步获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public synchronized static String doSyncPost(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, DEFAULTCHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :"
                        + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error("接口调用失败！", e);
            e.printStackTrace();
        }
        return null;
    }

    public static String doPostXml(String url, String xmlData) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity myEntity = new StringEntity(xmlData, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(myEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                InputStreamReader reader = new InputStreamReader(entity.getContent(), "UTF-8");
                char[] buff = new char[1024];
                int length = 0;
                while ((length = reader.read(buff)) != -1) {
                    result = new String(buff, 0, length);
                }
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error("接口调用失败！", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用okr接口
     *
     * @param url    地址
     * @param method 请求方式
     * @param param  参数
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String doPostForOkr(String url, String method, String param, Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder buf = new StringBuilder();
        buf.append(url).append("?").append("appid=")
                .append(params.get("appid")).append("&timestamp=")
                .append(params.get("timestamp")).append("&signature=")
                .append(params.get("signature"));

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpMethod requestMethod = null;
        if (method.equalsIgnoreCase("post")) {
            requestMethod = HttpMethod.POST;
        } else {
            requestMethod = HttpMethod.GET;
        }

        org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity(param, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(buf.toString(), requestMethod, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "";
        }
    }

    public static final String GET_METHOD = "GET";

    public static final String POST_METHOD = "POST";

    public static final String DEFAULT_CHARSET = "utf-8";

    private static int DEFAULT_CONNTIME = 5000;

    private static int DEFAULT_READTIME = 5000;


    /**
     * http请求
     *
     * @param method
     *            请求方法GET/POST
     * @param path
     *            请求路径
     * @param timeout
     *            连接超时时间 默认为5000
     * @param readTimeout
     *            读取超时时间 默认为5000
     * @param data  数据
     * @return
     */
    public static String defaultConnection(String method, String path, int timeout, int readTimeout, String data)
            throws Exception {
        String result = "";
        URL url = new URL(path);
        if (url != null) {
            HttpURLConnection conn = getConnection(method, url);
            conn.setConnectTimeout(timeout == 0 ? DEFAULT_CONNTIME : timeout);
            conn.setReadTimeout(readTimeout == 0 ? DEFAULT_READTIME : readTimeout);
            if (data != null && !"".equals(data)) {
                OutputStream output = conn.getOutputStream();
                output.write(data.getBytes(DEFAULT_CHARSET));
                output.flush();
                output.close();
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                result = inputToStr(input);
                input.close();
                conn.disconnect();
            }
        }
        return result;
    }

    /**
     * 根据url的协议选择对应的请求方式 例如 http://www.baidu.com 则使用http请求,https://www.baidu.com
     * 则使用https请求
     *
     * @param method
     *            请求的方法
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(String method, URL url) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext context = null;
            try {
                context = SSLContext.getInstance("SSL", "SunJSSE");
                context.init(new KeyManager[0], new TrustManager[] { new MyX509TrustManager() },
                        new java.security.SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(context.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

    /**
     * 将输入流转换为字符串
     *
     * @param input
     *            输入流
     * @return 转换后的字符串
     */
    public static String inputToStr(InputStream input) {
        String result = "";
        if (input != null) {
            byte[] array = new byte[1024];
            StringBuffer buffer = new StringBuffer();
            try {
                for (int index; (index = (input.read(array))) > -1;) {
                    buffer.append(new String(array, 0, index, DEFAULT_CHARSET));
                }
                result = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = "";
            }
        }
        return result;
    }

    /**
     * 设置参数
     *
     * @param map
     *            参数map
     * @param path
     *            需要赋值的path
     * @param charset
     *            编码格式 默认编码为utf-8(取消默认)
     * @return 已经赋值好的url 只需要访问即可
     */
    public static String setParmas(Map<String, String> map, String path, String charset) throws Exception {
        String result = "";
        boolean hasParams = false;
        if (path != null && !"".equals(path)) {
            if (map != null && map.size() > 0) {
                StringBuilder builder = new StringBuilder();
                Set<Entry<String, String>> params = map.entrySet();
                for (Entry<String, String> entry : params) {
                    String key = entry.getKey().trim();
                    String value = entry.getValue().trim();
                    if (hasParams) {
                        builder.append("&");
                    } else {
                        hasParams = true;
                    }
                    if(charset != null && !"".equals(charset)){
                        //builder.append(key).append("=").append(URLDecoder.(value, charset));
                        builder.append(key).append("=").append(urlEncode(value, charset));
                    }else{
                        builder.append(key).append("=").append(value);
                    }
                }
                result = builder.toString();
            }
        }
        return doUrlPath(path, result).toString();
    }

    /**
     * 设置连接参数
     *
     * @param path
     *            路径
     * @return
     */
    private static URL doUrlPath(String path, String query) throws Exception {
        URL url = new URL(path);
        if (org.apache.commons.lang.StringUtils.isEmpty(path)) {
            return url;
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(url.getQuery())) {
            if (path.endsWith("?")) {
                path += query;
            } else {
                path = path + "?" + query;
            }
        } else {
            if (path.endsWith("&")) {
                path += query;
            } else {
                path = path + "&" + query;
            }
        }
        return new URL(path);
    }

    /**
     * 默认的http请求执行方法,返回
     *
     * @param method
     *            请求的方法 POST/GET
     * @param path
     *            请求path 路径
     * @param map
     *            请求参数集合
     * @param data
     *            输入的数据 允许为空
     * @return
     */
    public static String HttpDefaultExecute(String method, String path, Map<String, String> map, String data) {
        String result = "";
        try {
            String url = setParmas((TreeMap<String, String>) map, path, "");
            result = defaultConnection(method, url, DEFAULT_CONNTIME, DEFAULT_READTIME, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 默认的https执行方法,返回
     *
     * @param method
     *            请求的方法 POST/GET
     * @param path
     *            请求path 路径
     * @param map
     *            请求参数集合
     * @param data
     *            输入的数据 允许为空
     * @return
     */
    public static String HttpsDefaultExecute(String method, String path, Map<String, String> map, String data) {
        String result = "";
        try {
            String url = setParmas((TreeMap<String, String>) map, path, "");
            result = defaultConnection(method, url, DEFAULT_CONNTIME, DEFAULT_READTIME, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String urlEncode(String source, String encode) {
        String result = source;
        try {
            result = URLEncoder.encode(source, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}