//
//package com.android.netconnect.engine.NetWork.Impl;
//
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.HttpVersion;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.params.HttpClientParams;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.params.ConnManagerParams;
//import org.apache.http.conn.params.ConnPerRouteBean;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.params.HttpProtocolParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//import android.text.TextUtils;
//
//import com.zry.common.ConstantValue;
//import com.zry.base.common.utils.LogUtil;
//import com.android.netconnect.NetConstant;
//import IRequest;
//import RequestMethod;
//import IHttpResult;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * 关于HTTPClient的操作
// *
// * @author zhaoruyang
// */
//public class ClientConnectImpl implements IRequest {
//
//    private Header[] headers;
//    /**
//     * 自定义Cookie值设置
//     */
//    private String cookie;
//
//    public String getCookie() {
//        return cookie;
//    }
//
//    public void setCookie(String cookie) {
//        this.cookie = cookie;
//    }
//
//    /**
//     * 请求类型
//     */
//
//    private HttpClient httpClient;
//
//    @Override
//    public void doGet(String url, IHttpResult resultDeal) {
//        HttpGet httpGet = new HttpGet(url);
//
//        try {
//            if (TextUtils.isEmpty(cookie)) {
//                httpGet.setHeader("Cookie", cookie);
//            }
//            /** 执行GET请求 */
//            HttpResponse response = httpClient.execute(httpGet);
//            int statusCode = response.getStatusLine().getStatusCode();
//            /** 判断响应的状态码: 200代表响应成功 */
//            if (statusCode == HttpStatus.SC_OK) {
//                /** 返回响应数据 */
//                resultDeal.requestSuccess(RequestMethod.GET, new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF-8")));
//            } else {
//                resultDeal.requestFail(statusCode, null);
//            }
//        } catch (IOException e) {
//            resultDeal.requestFail(NetConstant.ERROR_EXCEPTION, e);
//            LogUtil.e(ValueTAG.EXCEPTION, "*****EXCEPTION*****\n", e);
//        } finally {
//            httpGet.abort();
//            /** 用于关闭所有过期的连接 */
//            httpClient.getConnectionManager().closeExpiredConnections();
//        }
//    }
//
//    @Override
//    public void doPost(String url, Map<String, String> params, IHttpResult resultDeal) {
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            /** 设置请求参数 */
//            if (params != null && params.size() > 0) {
//
//                /** 将map转化成list集合 */
//                List<NameValuePair> paramLists = new ArrayList<>();
//                for (Map.Entry<String, String> map : params.entrySet()) {
//                    paramLists.add(new BasicNameValuePair(map.getKey(), map.getValue()));
//                }
//                /** 为POST请设置请求参数 */
//                httpPost.setEntity(new UrlEncodedFormEntity(paramLists, "UTF-8"));
//
//            }
//            /** 执行post请求 */
//            HttpResponse response = httpClient.execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            /** 对响应的状态做判断 */
//            if (statusCode == HttpStatus.SC_OK) {
//                /** 返回响应数据 */
//                resultDeal.requestSuccess(RequestMethod.GET, new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF-8")));
//            } else {
//                resultDeal.requestFail(statusCode, null);
//            }
//        } catch (IOException e) {
//            resultDeal.requestFail(NetConstant.ERROR_EXCEPTION, e);
//            LogUtil.e(ValueTAG.EXCEPTION, "*****EXCEPTION*****\n", e);
//        } finally {
//            LogUtil.i(NetConstant.TAG_NET_CONNECT, "release");
//            httpPost.abort();
//            httpClient.getConnectionManager().closeExpiredConnections();
//        }
//    }
//
//
//    // private constructor suppresses
//    public ClientConnectImpl() {
//        httpClient = getHttpClient();
//        headers = new Header[10];
//
////        headers[0] = new BasicHeader("screen", "");
////        headers[1] = new BasicHeader("ver", "");// 手机串号
////        headers[2] = new BasicHeader("platf", "android");//
////        headers[3] = new BasicHeader("imei", "");//
//    }
//
//
//    public HttpClient getHttpClient() {
//
//        HttpParams params = new BasicHttpParams();
//        // 设置一些基本参数
//        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//
//        /* 从连接池中取连接的超时时间 */
//        ConnManagerParams.setTimeout(params, 1000);
//        /* 连接超时 */
//        HttpConnectionParams.setConnectionTimeout(params, 5000);
//        /* 请求超时 */
//        HttpConnectionParams.setSoTimeout(params, 8000);
//
//        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//        HttpProtocolParams.setUseExpectContinue(params, true);
//        HttpConnectionParams.setStaleCheckingEnabled(params, false);
//        HttpClientParams.setRedirecting(params, false);
//        // set max connections per host
//        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(15));
//        // set max total connections
//        ConnManagerParams.setMaxTotalConnections(params, 115);
//        // set user agent
//        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
//        HttpProtocolParams.setUserAgent(params, userAgent);
//
//        // disable Nagle algorithm
//        HttpConnectionParams.setTcpNoDelay(params, true);
//
////        HttpConnectionParams.setSocketBufferSize(params, DEFAULT_SOCKET_BUFFER_SIZE);
//
//        // scheme: http and https
//        SchemeRegistry schemeRegistry = new SchemeRegistry();
//        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
//
//
//        // 使用线程安全的连接管理来创建HttpClient
//        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schemeRegistry);
//        return new DefaultHttpClient(conMgr, params);
//
//    }
//
//}
