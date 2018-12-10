package com.news.service.implement.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.news.common.ResultWrapper;
import com.news.service.implement.IOssService;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OssService implements IOssService{
    private static final long serialVersionUID = 5522372203700422672L;


    /**
     * 上传
     * @param request
     * @param response
     */
    public void ossUploader(HttpServletRequest request, HttpServletResponse response){
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
        String accessId = "LTAIUNlz4Cq219NR";
        String accessKey = "smzNeRlzyXQ0pvDv4AG9E0BpsUYsmU";
        String bucket = "tinelion-video";
        String dir = "myvideo/";
        String callBackURL = "/upload/callback";
        String host = "http://" + bucket + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Callback callback = new Callback();
            callback.setCallbackUrl("http://123.207.231.159:8081/upload/callback");
            callback.setCallbackHost("123.207.231.159:8081");
            callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
            String callbackString = Base64.encodeBase64String(JSONObject.fromObject(callback).toString().getBytes());

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("callback",callbackString);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            JSONObject ja1 = JSONObject.fromObject(respMap);
            System.out.println(ja1.toString());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST");
            responseU(request, response, ja1.toString());

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void responseU(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
        String callbackFunName = request.getParameter("callback");
        if (callbackFunName==null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( "+results+" )");
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }

    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject object = new JSONObject();
        object.put("code",200);
        object.put("message","success");
        String results = JSONObject.fromObject(object).toString();
        this.responseC(request, response, results, 200);
    }

    private void responseC(HttpServletRequest request, HttpServletResponse response, String results, int status) throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(status);
        response.flushBuffer();
    }
}
