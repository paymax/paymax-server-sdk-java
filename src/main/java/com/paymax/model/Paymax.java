package com.paymax.model;

import com.alibaba.fastjson.JSON;
import com.paymax.config.PaymaxConfig;
import com.paymax.config.SignConfig;
import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.sign.HttpRequestWrapper;
import com.paymax.sign.RSA;
import com.paymax.sign.Request;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xiaowei.wang on 2016/4/26.
 */
public abstract class Paymax extends PaymaxBase {

    private static String HEADER_KEY_NONCE = "nonce";
    private static String HEADER_KEY_TIMESTAMP = "timestamp";
    private static String HEADER_KEY_AUTHORIZATION = "Authorization";
    private static String REQUEST_SUCCESS_FLAG = "reqSuccessFlag";
    private static String RESPONSE_CODE = "code";
    private static String RESPONSE_DATA = "data";
    private static int VALID_RESPONSE_TTL = 2 * 60 * 1000;//合法响应时间:2分钟内


    private static CloseableHttpClient httpsClient = null;

    static class AnyTrustStrategy implements TrustStrategy {

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }

    }

    static {
        try {
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
            ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
            registryBuilder.register("http", plainSF);

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            SSLContext sslContext =
                    SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
            LayeredConnectionSocketFactory sslSF =
                    new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            registryBuilder.register("https", sslSF);

            Registry<ConnectionSocketFactory> registry = registryBuilder.build();

            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
            connManager.setMaxTotal(500);

            httpsClient = HttpClientBuilder.create().setConnectionManager(connManager).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param url
     * @param jsonReqData
     * @param clazz
     * @param <T>
     * @return
     * @throws AuthorizationException
     * @throws IOException
     * @throws InvalidRequestException
     */
    protected static <T> T request(String url, String jsonReqData, Class<T> clazz) throws AuthorizationException, IOException, InvalidRequestException, InvalidResponseException {
        AppInfo appInfo = makeAppInfoFromStatic();
        return request(appInfo, url, jsonReqData, clazz);
    }

    private static AppInfo makeAppInfoFromStatic() {
        AppInfo appInfo = new AppInfo();
        appInfo.setSecretKey(SignConfig.SECRET_KEY);
        appInfo.setPrivateKey(SignConfig.PRIVATE_KEY);
        appInfo.setPublicKey(SignConfig.PAYMAX_PUBLIC_KEY);
        return appInfo;
    }

    /**
     * 增加签名信息参数 by CJ
     *
     * @param appInfo     签名信息通过栈而不是静态传递
     * @param url
     * @param jsonReqData
     * @param clazz
     * @param <T>
     * @return
     * @throws AuthorizationException
     * @throws IOException
     * @throws InvalidRequestException
     */
    protected static <T> T request(AppInfo appInfo, String url, String jsonReqData, Class<T> clazz) throws AuthorizationException, IOException, InvalidRequestException, InvalidResponseException {

        if (StringUtils.isBlank(appInfo.getSecretKey())) {
            throw new AuthorizationException("Secret key can not be blank.Please set your Secret key in com.Paymax.config.SignConfig");
        }

        if (StringUtils.isBlank(appInfo.getPrivateKey())) {
            throw new AuthorizationException("RSA Private key can not be blank.Please set your RSA Private key  in com.Paymax.config.SignConfig");
        }

        if (StringUtils.isBlank(appInfo.getPublicKey())) {
            throw new InvalidRequestException("Paymax Public key can not be blank.Please set your Paymax Public key in com.Paymax.config.SignConfig");
        }
        Map<String, String> result = null;
        if (StringUtils.isBlank(jsonReqData)) {
            result = buildGetRequest(appInfo, url);
        } else {
            if (clazz.getSimpleName().equals("String")) {
                //noinspection unchecked
                return (T) buildDownloadPostRequest(appInfo,url, jsonReqData);
            } else {
                result = buildPostRequest(appInfo,url, jsonReqData);
            }
        }

        return dealWithResult(result, clazz);

    }

    /**
     * 处理返回数据
     *
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T dealWithResult(Map<String, String> result, Class<T> clazz) {
        int resultCode = Integer.valueOf(result.get(RESPONSE_CODE)).intValue();
        String resultData = result.get(RESPONSE_DATA);

        T t = JSON.parseObject(resultData, clazz);

        try {
            if (t == null) {
                t = clazz.newInstance();
            }
            Field f = clazz.getDeclaredField(REQUEST_SUCCESS_FLAG);
            f.setAccessible(true);
            f.set(t, resultCode < 400);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return t;

    }

    /**
     * POST请求
     *
     * @param url
     * @param jsonReqData
     * @return
     * @throws IOException
     * @throws InvalidResponseException
     */
    private static Map<String, String> buildPostRequest(AppInfo appInfo,String url, String jsonReqData) throws IOException, InvalidResponseException, InvalidRequestException {
        Map<String, String> result = null;

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(
                new ByteArrayEntity(jsonReqData.getBytes(Charset.forName(PaymaxConfig.CHARSET)), ContentType.APPLICATION_JSON));

        setCustomHeaders(appInfo,httpPost);

        String sign = signData(appInfo,httpPost);

        httpPost.addHeader(PaymaxConfig.SIGN, sign);

        CloseableHttpResponse response = httpsClient.execute(httpPost);
        try {
            result = verifyData(appInfo,response);
        } finally {
            response.close();
            httpPost.releaseConnection();
        }

        return result;

    }

    /**
     * 对账单下载POST请求
     *
     * @param url
     * @param jsonReqData
     * @return
     * @throws IOException
     * @throws InvalidResponseException
     */
    private static String buildDownloadPostRequest(AppInfo appInfo, String url, String jsonReqData) throws IOException
            , InvalidResponseException {

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(
                new ByteArrayEntity(jsonReqData.getBytes(Charset.forName(PaymaxConfig.CHARSET)), ContentType.APPLICATION_JSON));

        setCustomHeaders(appInfo,httpPost);

        String sign = signData(appInfo,httpPost);

        httpPost.addHeader(PaymaxConfig.SIGN, sign);

        CloseableHttpResponse response = httpsClient.execute(httpPost);

        try {
            return EntityUtils.toString(response.getEntity(), Charset.forName(PaymaxConfig.CHARSET));
        } finally {
            response.close();
            httpPost.releaseConnection();
        }
    }

    /**
     * 对返回结果进行验签。
     * 验签成功:返回数据
     * 验签失败:抛出异常
     *
     * @param response
     * @return
     * @throws IOException
     * @throws InvalidResponseException
     */
    private static Map<String, String> verifyData(AppInfo appInfo, CloseableHttpResponse response) throws IOException, InvalidResponseException, InvalidRequestException {
        Map<String, String> result = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {

                String resData = EntityUtils.toString(entity, Charset.forName(PaymaxConfig.CHARSET));
                int responseCode = response.getStatusLine().getStatusCode();
                if (Integer.valueOf(responseCode) < 400) {
                    String nonce = response.getFirstHeader(HEADER_KEY_NONCE) != null ? response.getFirstHeader(HEADER_KEY_NONCE).getValue() : "";
                    String timestamp = response.getFirstHeader(HEADER_KEY_TIMESTAMP) != null ? response.getFirstHeader(HEADER_KEY_TIMESTAMP).getValue() : "";
                    String secretKey = response.getFirstHeader(HEADER_KEY_AUTHORIZATION) != null ? response.getFirstHeader(HEADER_KEY_AUTHORIZATION).getValue() : "";

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    out.write(nonce.getBytes(PaymaxConfig.CHARSET));
                    out.write('\n');//header
                    out.write(timestamp.getBytes(PaymaxConfig.CHARSET));
                    out.write('\n');//header
                    out.write(secretKey.getBytes(PaymaxConfig.CHARSET));
                    out.write('\n');//header
                    byte[] data = resData.getBytes(PaymaxConfig.CHARSET);
                    out.write(data);//body
                    out.close();

                    String toVerifyData = out.toString(PaymaxConfig.CHARSET);

                    String sign = response.getFirstHeader(PaymaxConfig.SIGN) != null ? response.getFirstHeader(PaymaxConfig.SIGN).getValue() : "";

                    boolean flag = RSA.verify(toVerifyData, sign, appInfo.getPublicKey());
                    if (!flag) {
                        throw new InvalidResponseException("Invalid Response.[Response Data And Sign Verify Failure.]");
                    }

                    if (!appInfo.getSecretKey().equals(secretKey)) {
                        throw new InvalidResponseException("Invalid Response.[Secret Key Is Invalid.]");
                    }

                    if (Long.valueOf(timestamp) + VALID_RESPONSE_TTL < System.currentTimeMillis()) {
                        throw new InvalidResponseException("Invalid Response.[Response Time Is Invalid.]");
                    }
                } else {
                    // 不再返回「非正常」结果，代替以异常
                    throw new InvalidRequestException(resData);
                }


                result = new HashMap<String, String>();

                result.put(RESPONSE_CODE, String.valueOf(responseCode));
                result.put(RESPONSE_DATA, resData);

            } finally {
                EntityUtils.consumeQuietly(entity);
            }

        }

        return result;
    }


    /**
     * GET请求
     *
     * @param appInfo
     * @param url
     * @return
     * @throws IOException
     * @throws InvalidResponseException
     */
    private static Map<String, String> buildGetRequest(AppInfo appInfo, String url) throws IOException, InvalidResponseException, InvalidRequestException {
        Map<String, String> result = null;

        HttpGet httpGet = new HttpGet(url);

        setCustomHeaders(appInfo,httpGet);

        String sign = signData(appInfo,httpGet);

        httpGet.addHeader(PaymaxConfig.SIGN, sign);

        CloseableHttpResponse response = httpsClient.execute(httpGet);
        try {
            result = verifyData(appInfo,response);
        } finally {
            response.close();
            httpGet.releaseConnection();
        }

        return result;
    }

    /**
     * 设置header
     * <p>
     * Request Header
     *
     * @param request
     */
    private static void setCustomHeaders(AppInfo appInfo, HttpRequestBase request) {
        request.addHeader("Content-Type", "application/json; charset=" + PaymaxConfig.CHARSET);
        request.addHeader(HEADER_KEY_AUTHORIZATION, appInfo.getSecretKey());

        String timestamp = String.valueOf(System.currentTimeMillis());
        request.addHeader(HEADER_KEY_TIMESTAMP, timestamp);
        request.addHeader(HEADER_KEY_NONCE, UUID.randomUUID().toString().replaceAll("-", ""));

        String[] propertyNames = {"os.name", "os.version", "os.arch",
                "java.version", "java.vendor", "java.vm.version",
                "java.vm.vendor"};
        Map<String, String> propertyMap = new HashMap<String, String>();
        for (String propertyName : propertyNames) {
            propertyMap.put(propertyName, System.getProperty(propertyName));
        }
        propertyMap.put("lang", "Java");
        propertyMap.put("publisher", "Paymax");
        propertyMap.put("sdk-version", PaymaxConfig.SDK_VERSION);

        request.addHeader("X-Paymax-Client-User-Agent", JSON.toJSONString(propertyMap));

    }

    /**
     * 签名数据
     *
     * @param httpRequest
     * @return
     * @throws IOException
     */
    private static String signData(AppInfo appInfo, HttpRequestBase httpRequest) throws IOException {
        Request<HttpRequest> request = new HttpRequestWrapper(
                httpRequest);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(request.getMethod().toLowerCase().getBytes(PaymaxConfig.CHARSET));
        out.write('\n');//method
        String uri = request.getRequestUriPath();
        if (StringUtils.isBlank(uri)) {
            uri = "";
        }
        int _index = uri.indexOf("/v1/");
        if (_index != -1) {
            uri = uri.substring(_index);
        }
        out.write(uri.getBytes(PaymaxConfig.CHARSET));
        out.write('\n');//uri path
        out.write(request.getRequestQueryString().getBytes(PaymaxConfig.CHARSET));
        out.write('\n');//query string
        out.write(request.getHeaderValue(HEADER_KEY_NONCE).getBytes(PaymaxConfig.CHARSET));
        out.write('\n');//header
        out.write(request.getHeaderValue(HEADER_KEY_TIMESTAMP).getBytes(PaymaxConfig.CHARSET));
        out.write('\n');//header
        out.write(request.getHeaderValue(HEADER_KEY_AUTHORIZATION).getBytes(PaymaxConfig.CHARSET));
        out.write('\n');//header
        byte[] data = IOUtils.toByteArray(request.getRequestBody());
        out.write(data);//body
        out.close();
        String toSignString = out.toString(PaymaxConfig.CHARSET);
        return RSA.sign(toSignString, appInfo.getPrivateKey());
    }

}