package io.smallbird.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpRequestUtil {

	private static CloseableHttpClient httpClient;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		cm.setDefaultMaxPerRoute(20);
		cm.setDefaultMaxPerRoute(50);
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static String post(String url, Map<String, String> map) {
		CloseableHttpResponse response = null;
		BufferedReader in = null;
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000)
			        .build();
			httpPost.setConfig(requestConfig);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
			httpPost.setHeader("Accept", "application/json");

			List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			for (Entry<String, String> entry : map.entrySet()) {
				pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
			response = httpClient.execute(httpPost);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			result = sb.toString();
		}
		catch (IOException e) {
			log.error("网络异常", e, HttpRequestUtil.class);
			return StringUtils.EMPTY;
		}
		finally {
			try {
				if (null != response) {
					response.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}