package com.changfa.frame.core.util;

import net.sf.json.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static final int TIME_OUT = 60000;

    private static RequestConfig.Builder requestConfigBuilder = null;

    /**
     * 创建https协议的client
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setDefaultRequestConfig(getDefaultRequestConfig()).setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            log.error("创建SSLClientDefault失败:{}", ExceptionUtils.getFullStackTrace(e));
        } catch (NoSuchAlgorithmException e) {
            log.error("创建SSLClientDefault失败:{}" + ExceptionUtils.getFullStackTrace(e));
        } catch (KeyStoreException e) {
            log.error("创建SSLClientDefault失败:{}" + ExceptionUtils.getFullStackTrace(e));
        }
        return createClientDefault();
    }

    /**
     * 创建https协议的client
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient createClientDefault() {
        return HttpClients.custom().setDefaultRequestConfig(getDefaultRequestConfig()).build();
    }

    /**
     * 获取默认Request配置
     *
     * @return
     */
    private static RequestConfig getDefaultRequestConfig() {
        if (requestConfigBuilder == null) {
            requestConfigBuilder = RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT).setStaleConnectionCheckEnabled(true);
        }
        return requestConfigBuilder.build();
    }

    /**
     * Http协议POST请求
     *
     * @param url          请求地址
     * @param parameterMap 请求参数
     * @param charsetCode  编码类型
     * @return
     */
    public static String post(String url, Map<String, Object> parameterMap, String charsetCode) {
        // 参数为空判断
        Assert.hasText(url, "Http请求URL为空");

        // 初始化Http客户端
        String result = null;
        CloseableHttpClient httpClient = createClientDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = getNameValuePair(parameterMap);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charsetCode));
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, charsetCode);
            EntityUtils.consume(httpEntity);
        } catch (Exception e) {
            log.error("HTTP_POST【ParamMap】请求失败:{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("HTTP_POST【ParamMap】关闭httpClient失败：{}", ExceptionUtils.getFullStackTrace(e));
            }
        }

        return result;
    }

    /**
     * Http协议POST请求
     *
     * @param url         请求地址
     * @param paramStr    请求参数
     * @param charsetCode 编码类型
     * @return
     */
    public static String post(String url, String paramStr, String charsetCode) {
        // 参数为空判断
        Assert.hasText(url, "Http请求URL为空");

        // 初始化Http客户端
        String result = null;
        CloseableHttpClient httpClient = createClientDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(paramStr, charsetCode));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, charsetCode);
            EntityUtils.consume(httpEntity);
        } catch (Exception e) {
            log.error("HTTP_POST【paramStr】请求失败:{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("HTTP_POST【paramStr】关闭httpClient失败：{}", ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }


    /**
     * Http协议POST请求
     *
     * @param url          请求URL
     * @param parameterMap 参数集合
     * @return
     */
    public static String post(String url, Map<String, Object> parameterMap) {
        return post(url, parameterMap, "UTF-8");
    }

    /**
     * Http协议POST请求
     *
     * @param url      请求URL
     * @param paramStr 字符串参数
     * @return
     */
    public static String post(String url, String paramStr) {
        return post(url, paramStr, "UTF-8");
    }

    /**
     * Http协议GET请求
     *
     * @param url          请求URL
     * @param parameterMap 参数集合
     * @param headerMap    请求头参数集合
     * @param charsetCode  编码
     * @return
     */
    public static String get(String url, Map<String, Object> parameterMap, Map<String, Object> headerMap, String charsetCode) {
        // 参数为空判断
        Assert.hasText(url, "Http请求URL为空");

        // 初始化HTTP客户端
        String result = null;
        CloseableHttpClient httpClient = createClientDefault();
        try {
            // 获取请求体名值对List参数集合
            List<NameValuePair> nameValuePairs = getNameValuePair(parameterMap);
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, charsetCode)));

            //添加header参数
            if (MapUtils.isNotEmpty(headerMap)) {
                Set<Map.Entry<String, Object>> headerEntries = headerMap.entrySet();
                for (Map.Entry<String, Object> headerEntry : headerEntries) {
                    httpGet.setHeader(headerEntry.getKey(), ConvertUtils.convert(headerEntry.getValue()));
                }
            }

            // HTTP协议GET请求
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, charsetCode);
            EntityUtils.consume(httpEntity);
        } catch (Exception e) {
            log.error("\r\n *******************HTTP_GET【ParamMap】请求失败：{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("\r\n *******************HTTP_GET【ParamMap】关闭httpClient失败：{}", ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }

    /**
     * Http协议GET请求
     *
     * @param url          Http请求URL
     * @param parameterMap HTTP请求参数集合
     * @param charsetCode  HTTP请求编码
     * @return String 返回字符串
     */
    public static String get(String url, Map<String, Object> parameterMap, String charsetCode) {
        return get(url, parameterMap, null, charsetCode);
    }

    /**
     * Http协议GET请求
     *
     * @param url          Http请求URL
     * @param parameterMap HTTP请求参数集合
     * @return String 返回字符串
     */
    public static String get(String url, Map<String, Object> parameterMap) {
        return get(url, parameterMap, "UTF-8");
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url          Https请求URL
     * @param parameterMap Https请求参数集合
     * @param charsetCode  Https请求编码
     * @return String 返回字符串
     */
    public static String httpsPost(String url, Map<String, Object> parameterMap, String charsetCode) {
        // 初始化HTTPS客户端
        CloseableHttpClient httpsClient = createSSLClientDefault();
        String result = null;
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairs = getNameValuePair(parameterMap);
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, charsetCode));
            HttpResponse response = httpsClient.execute(post);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, charsetCode);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("\r\n *****************HTTP_POST【parameterMap】请求失败:{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpsClient.close();
            } catch (IOException e) {
                log.error("\r\n *****************HTTP_POST【parameterMap】关闭httpClient失败:{}", ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url         Https请求URL
     * @param paramList   HTTPS请求参数集合
     * @param charsetCode HTTPS请求编码
     * @return
     */
    public static String httpsPost(String url, List<String[]> paramList, String charsetCode) {
        // 初始化HTTP客户端
        CloseableHttpClient httpsClient = createSSLClientDefault();
        String result = null;
        try {
            // 初始化HTTPS请求参数
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairs = getNameValuePair(paramList);
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, charsetCode));

            // 发起HTTPS请求
            HttpResponse response = httpsClient.execute(post);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, charsetCode);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("\r\n *****************HTTP_POST【paramList】请求失败:{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpsClient.close();
            } catch (IOException e) {
                log.error("\r\n *****************HTTP_POST【paramList】关闭httpClient失败：{}", ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url         Https请求URL
     * @param paramStr    HTTPS请求参数集合
     * @param charsetCode HTTPS请求编码
     * @return
     */
    public static String httpsPost(String url, String paramStr, String charsetCode) {
        // 初始化HTTPS请求客户端
        CloseableHttpClient httpsClient = createSSLClientDefault();
        String result = null;
        try {
            HttpPost post = new HttpPost(url);
//            StringEntity stringEntity = new StringEntity(paramStr, charsetCode);
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(paramStr.getBytes("UTF-8"));
            post.setEntity(byteArrayEntity);
            HttpResponse response = httpsClient.execute(post);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, charsetCode);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("\r\n *****************HTTP_POST【paramStr】请求失败:{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpsClient.close();
            } catch (IOException e) {
                log.error("*****************HTTP_POST【paramStr】关闭httpClient失败：{}", ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url          Https请求URL
     * @param parameterMap HTTPS请求参数集合
     * @return
     */
    public static String httpsPost(String url, Map<String, Object> parameterMap) {
        return httpsPost(url, parameterMap, "UTF-8");
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url    Https请求URL
     * @param params HTTPS请求参数集合
     * @return
     */
    public static String httpsPost(String url, List<String[]> params) {
        return httpsPost(url, params, "UTF-8");
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url     HTTPS请求URL
     * @param postStr HTTPS请求参数集合
     * @return
     */
    public static String httpsPost(String url, String postStr) {
        return httpsPost(url, postStr, "UTF-8");
    }

    /**
     * HTTPS协议GET请求
     *
     * @param url          Https请求URL
     * @param parameterMap HTTPS请求参数集合
     * @param charsetCode  HTTPS请求编码
     * @return
     */
    public static String httpsGet(String url, Map<String, Object> parameterMap, String charsetCode) {
        // 初始化HTTPS请求客户端
        CloseableHttpClient httpsClient = createSSLClientDefault();
        String result = null;
        try {
            List<NameValuePair> nameValuePairs = getNameValuePair(parameterMap);
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, charsetCode)));
            HttpResponse response = httpsClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, charsetCode);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("\r\n ***************** HTTPS_GET【parameterMap】请求失败：{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpsClient.close();
            } catch (IOException e) {
                log.error("\r\n ***************** HTTPS_GET【parameterMap】关闭httpClient失败：" + ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }

    /**
     * HTTPS协议GET请求
     *
     * @param url          Https请求URL
     * @param parameterMap HTTPS请求参数集合
     * @param charsetCode  HTTPS请求编码
     * @return
     */
    public static byte[] httpsGet(String charsetCode, String url, Map<String, Object> parameterMap) {
        // 初始化HTTPS请求客户端
        CloseableHttpClient httpsClient = createSSLClientDefault();
        byte[] result = null;
        try {
            List<NameValuePair> nameValuePairs = getNameValuePair(parameterMap);
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, charsetCode)));
            HttpResponse response = httpsClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity.isStreaming()) {
                result = EntityUtils.toByteArray(httpEntity);
            } else {
                log.info("\r\n ****** Https请求错误：{}", EntityUtils.toString(httpEntity, charsetCode));
            }
            EntityUtils.consume(httpEntity);
        } catch (Exception e) {
            log.error("\r\n ***************** HTTPS_GET【parameterMap】请求失败：{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                httpsClient.close();
            } catch (IOException e) {
                log.error("\r\n ***************** HTTPS_GET【parameterMap】关闭httpClient失败：" + ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }

    /**
     * HTTPS协议GET请求
     *
     * @param url         HTTPS请求URL
     * @param paramList   HTTPS请求参数集合
     * @param charsetCode HTTPS请求编码
     * @return
     */
    public static String httpsGet(String url, List<String[]> paramList, String charsetCode) {
        // 初始化HTTPS请求客户端
        CloseableHttpClient client = createSSLClientDefault();
        String result = null;
        try {
            List<NameValuePair> nameValuePairs = getNameValuePair(paramList);
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, charsetCode)));
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, charsetCode);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("\r\n ***************** HTTPS_GET【paramList】请求失败：{}", ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                log.error("\r\n ***************** HTTPS_GET【paramList】关闭httpClient失败：", ExceptionUtils.getFullStackTrace(e));
            }
        }
        return result;
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url          HTTPS协议POST请求
     * @param parameterMap HTTPS协议参数
     * @return
     */
    public static JSONObject getJSONObjectFromHttpsPost(String url, Map<String, Object> parameterMap) {
        try {
            return JSONObject.fromObject(httpsPost(url, parameterMap));
        } catch (Exception e) {
            log.error("\r\n ******************HTTPS_POST【parameterMap】请求出错：{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url       HTTPS协议POST请求
     * @param paramList HTTPS协议参数
     * @return
     */
    public static JSONObject getJSONObjectFromHttpsPost(String url, List<String[]> paramList) {
        try {
            return JSONObject.fromObject(httpsPost(url, paramList));
        } catch (Exception e) {
            log.error("\r\n ******************HTTPS_POST【paramList】请求出错：{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * HTTPS协议POST请求
     *
     * @param url      HTTPS请求URL
     * @param paramStr HTTPS请求参数集合
     * @return
     */
    public static JSONObject getJSONObjectFromHttpsPost(String url, String paramStr) {
        try {
            return JSONObject.fromObject(httpsPost(url, paramStr));
        } catch (Exception e) {
            log.error("\r\n ******************HTTPS_POST【paramStr】请求出错：{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * HTTPS协议GET请求
     *
     * @param url          HTTPS请求URL
     * @param parameterMap HTTPS请求参数集合
     * @return
     */
    public static JSONObject getJSONObjectFromHttpsGet(String url, Map<String, Object> parameterMap) {
        try {
            return JSONObject.fromObject(httpsGet(url, parameterMap, "UTF-8"));
        } catch (Exception e) {
            log.error("\r\n ******************HTTPS_GET【parameterMap】请求出错：{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * HTTPS协议GET请求
     *
     * @param url       HTTPS请求URL
     * @param paramList HTTPS请求参数集合
     * @return
     */
    public static JSONObject getJSONObjectFromHttpsGet(String url, List<String[]> paramList) {
        try {
            return JSONObject.fromObject(httpsGet(url, paramList, "UTF-8"));
        } catch (Exception e) {
            log.error("\r\n ******************HTTPS_GET【paramList】请求出错：{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * 获取post请求，并将响应变更为jsonObject
     *
     * @param url          请求地址
     * @param parameterMap 参数
     * @param charsetCode  编码
     * @return
     */
    public static JSONObject getJSONObjectFromPost(String url, Map<String, Object> parameterMap, String charsetCode) {
        return JSONObject.fromObject(post(url, parameterMap, charsetCode));
    }

    /**
     * 获取post请求，并将响应变更为jsonObject
     *
     * @param url          请求地址
     * @param parameterMap 参数
     * @return
     */
    public static JSONObject getJSONObjectFromPost(String url, Map<String, Object> parameterMap) {
        return getJSONObjectFromPost(url, parameterMap, "UTF-8");
    }

    /**
     * 发起带有CA认证的https的post请求，默认utf-8编码(仅用于含有需要证书认证的微信支付支付接口)
     *
     * @param url         微信接口URL
     * @param postStr     微信接口参数（XML格式）
     * @param certPath    微信退款CA证书路径
     * @param certassword 微信退款证书密码（默认密码为商户号）
     * @return
     */
    public static String httpsPostWithCA(String url, String postStr, String certPath, String certassword) {
        return httpsPostWithCA(url, postStr, certPath, certassword, "UTF-8");
    }

    /**
     * 发起带有CA认证的https的post请求，默认utf-8编码(仅用于含有需要证书认证的微信支付支付接口),返回XML字符串
     *
     * @param url          微信退款接口URL
     * @param postStr      微信退款接口参数
     * @param charsetCode  接口调用编码格式（默认UTF-8）
     * @param certPath     CA证书路径
     * @param certPassword CA证书密码
     * @return
     */
    public static String httpsPostWithCA(String url, String postStr, String certPath, String certPassword, String charsetCode) {

        //初始化返回变量
        String result = null;

        //初始化KeyStore实例
        KeyStore keyStore = null;
        FileInputStream instream = null;
        char[] pwdChar = StringUtils.isNotBlank(certPassword) ? certPassword.toCharArray() : new char[]{};
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            instream = new FileInputStream(new File(certPath));
            keyStore.load(instream, pwdChar);
        } catch (Exception e) {
            log.error("\r\n **************************【httpsPostWithCA】接口KeyStore初始化失败START ************************ \r\n");
            log.error(ExceptionUtils.getFullStackTrace(e));
            log.error("\r\n *************************【httpsPostWithCA】接口KeyStore初始化失败END ************************ \r\n");
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                log.error("\r\n **************************【httpsPostWithCA】接口文件流关闭失败START ************************ \r\n");
                log.error(ExceptionUtils.getFullStackTrace(e));
                log.error("\r\n *************************【httpsPostWithCA】接口文件流关闭失败END ************************ \r\n");
                e.printStackTrace();
            }
        }

        // 依赖自己的微信证书
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, pwdChar).build();
        } catch (Exception e) {
            log.error("\r\n **************************【httpsPostWithCA】接口初始化证书SSL失败START ************************ \r\n");
            log.error(ExceptionUtils.getFullStackTrace(e));
            log.error("\r\n *************************【httpsPostWithCA】接口初始化证书SSL失败END ************************ \r\n");
            e.printStackTrace();
        }

        // 初始化证书认证的HttpClient
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(postStr, charsetCode));
            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, charsetCode);
            log.info("\r\n ************************** 退款接口返回信息START ************************ \r\n");
            log.info(result);
            log.info("\r\n ************************** 退款接口返回信息END ************************ \r\n");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("\r\n **************************【httpsPostWithCA】接口HttpPost请求失败START ************************ \r\n");
            log.error(ExceptionUtils.getFullStackTrace(e));
            log.error("\r\n **************************【httpsPostWithCA】接口HttpPost请求失败END ************************ \r\n");
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                log.error("\r\n **************************【httpsPostWithCA】接口HttpPost请求关闭失败START ************************ \r\n");
                log.error(ExceptionUtils.getFullStackTrace(e));
                log.error("\r\n **************************【httpsPostWithCA】接口HttpPost请求关闭失败END ************************ \r\n");
            }
        }

        return result;
    }


    /* ********************************************************* 对象内部私有方法 ********************************************************* */

    /**
     * 根据参数集合转化名值对
     *
     * @param parameterMap 参数集合
     * @return List<NameValuePair> 名值对集合
     */
    private static List<NameValuePair> getNameValuePair(Map<String, Object> parameterMap) {
        // 初始化返回集合
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        // 转化参数集合为名值对集合
        if (MapUtils.isNotEmpty(parameterMap)) {
            for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                String name = entry.getKey();
                String value = ConvertUtils.convert(entry.getValue());
                if (StringUtils.isNotEmpty(name)) {
                    nameValuePairs.add(new BasicNameValuePair(name, value));
                }
            }
        }
        return nameValuePairs;
    }

    /**
     * 根据参数集合转化名值对
     *
     * @param paramList 参数集合
     * @return List<NameValuePair> 名值对集合
     */
    private static List<NameValuePair> getNameValuePair(List<String[]> paramList) {
        // 初始化返回集合
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (paramList != null && !paramList.isEmpty()) {
            for (String[] arr : paramList) {
                nameValuePairs.add(new BasicNameValuePair(arr[0], arr[1]));
            }
        }
        return nameValuePairs;
    }

}