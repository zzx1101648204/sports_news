package com.news.controller;

import com.alibaba.fastjson.JSONObject;
import com.news.entitys.UserEntity;
import com.news.listenerfilterintercept.Listener;
import com.news.service.implement.IUserService;
import com.news.tool.CheckSumBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    Map map = new HashMap();
    //发送验证码的请求路径URL
    private static final String
            SERVER_URL = "https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String
            APP_KEY = "c416a2286913396277416f1b382bd3b4";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET = "70dcb32a39b0";
    //随机数
    private static final String NONCE = "123456";
    //短信模板ID
    private static final String TEMPLATEID = "3973403";
    //手机号
    // private static final String MOBILE="18781942015";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN = "6";
    @Autowired
    IUserService userService;
    @Autowired
    Listener listener;

    /**
     * 用户注册
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    public int createUser(@RequestParam(value = "phone") String phone, @RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password, @RequestParam(value = "objs") int objs) {
        long start = System.currentTimeMillis();
        if (System.currentTimeMillis() - start >1000 * 60 * 10) {
         map.remove("obj");
         map.remove("code");
        }

            try {

                if (((int) (map.get("obj"))) == (objs)&&String.valueOf(map.get("rePhone")).equals(phone)) {
                    int ch = userService.createUser(phone, userName, password);
                    if (ch == 1) {
                        return 1;
                    }
                    return 0;
                }
                return 0;
            } catch (Exception e) {
                return 0;
            }


    }

    /**
     * 修改用户数据(不包括用户权限）
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public int userInfoModify(@RequestParam("user") UserEntity user) {
        String userName = user.getUserName();
        if (userService.getUser(userName) != null)
            return -1;
        else
            return userService.userModify(user);
    }

    /**
     * 修改用户权限
     *
     * @param userName
     * @param power
     * @return
     */
    @RequestMapping(value = "/powerEdit", method = RequestMethod.POST)
    public int userPowerModify(String userName, int power) {
        Byte userPower = (byte) power;
        return userService.modifyUserAuth(userName, userPower);
    }

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    //   @Permission(identities = {1})
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public UserEntity findUser(String userName) {

        return userService.getUser(userName);
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object userLogin(@RequestParam String userName, @RequestParam String password,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserEntity userInfo = userService.getUser(userName);


        if (userInfo != null && userInfo.getPassword().equals(password)) {
            listener.loginSuccess(request, response);
            return 1;
        }
        return "login failed";
    }

    /**
     * 用户退出
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String userLogout(HttpServletRequest request, HttpServletResponse response) {
        listener.logoutSuccess(request, response);
        return "logout success";
    }

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public ModelAndView getLogPage() {

        return new ModelAndView("log");
    }

    //短信验证
    @PostMapping("/check")
    public void check(@RequestParam("phone") String MOBILE) throws Exception {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(SERVER_URL);
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
            String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

            // 设置请求的header
            httpPost.addHeader("AppKey", APP_KEY);
            httpPost.addHeader("Nonce", NONCE);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // 设置请求的的参数，requestBody参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
            nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
            nvps.add(new BasicNameValuePair("mobile", MOBILE));
            nvps.add(new BasicNameValuePair("codeLen", CODELEN));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            // 执行请求
            HttpResponse response_app = httpClient.execute(httpPost);
            //获取code，msg，obj（验证码）

            map = (Map) JSONObject.parse(EntityUtils.toString(response_app.getEntity()));
            String rePhone=MOBILE.substring(3,9);
            map.put("rePhone",MOBILE);
            System.out.println(EntityUtils.toString(response_app.getEntity(), "utf-8"));
        } catch (Exception e) {
            System.out.println("继续！");
        }


        /*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */

    }

}
