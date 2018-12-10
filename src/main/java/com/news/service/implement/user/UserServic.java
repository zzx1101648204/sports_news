package com.news.service.implement.user;

import com.news.dao.user.UserEntityMapper;
import com.news.entitys.UserEntity;
import com.news.service.implement.IUserService;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServic implements IUserService {
    //发送验证码的请求路径URL
    private static final String
            SERVER_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String
            APP_KEY="fd460d34e786e7754e505bc4fab0f027";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="dffdf7757248";
    //随机数
    private static final String NONCE="123456";
    //短信模板ID
    private static final String TEMPLATEID="3057527";
    //手机号
    private static final String MOBILE="13888888888";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN="6";


    @Autowired
    UserEntityMapper userDao;

    /**
     * 获取用户权限
     * @param unique
     * @return
     */

    public float[] getUserIdentities(String unique) {
        UserEntity userEntity = userDao.getUserByName(unique);
        float[] userIdentities;
        if(userEntity!=null)
            userIdentities = new float[]{userEntity.getPower()};
        else userIdentities = null;
        return userIdentities;
    }

    /**
     * 创建用户
     * @param
     * @return
     */
    public int createUser(String phone,String userName,String password){
   String userIco="A";
   String sign="该用户很饿，什么都没写！";
   byte power=1;


       int ch= userDao.createUser(phone,userName,password,userIco,sign,power);
        if(ch==1){return 1;}else {return 0;}
    }

    /**
     * 修改用户信息（不包括权限）
     * @param user
     * @return
     */
    public int userModify(UserEntity user){
        String userName = user.getUserName();
        UserEntity oldUserInfo = getUser(userName);
        String password = oldUserInfo.getPassword();
        String userIco = oldUserInfo.getUserIco();
        String sign = oldUserInfo.getSign();
        Byte power = oldUserInfo.getPower();

        if (user.getUserName()!=null)
            userName = user.getUserName();
        if (user.getPassword()!=null)
            password = user.getPassword();
        if (user.getSign()!=null)
            sign = user.getSign();
        if (user.getUserIco()!=null)
            userIco = user.getUserIco();
        if (user.getPower()!= null) {
            power = user.getPower();
        }
        if (userDao.getUserByName(userName)==null)
        return userDao.modifyUserByName(userName,password,userIco,sign,power);

        else return -1;
    }

    /**
     * 查找用户
     * @param userName
     * @return
     */
    public UserEntity getUser(String userName){
        return userDao.getUserByName(userName);
    }

    /**
     * 修改用户权限
     * @param userName
     * @param userPower
     * @return
     */
    public int modifyUserAuth(String userName, Byte userPower){
        UserEntity oldUserInfo = getUser(userName);
        String password = oldUserInfo.getPassword();
        String userIco = oldUserInfo.getUserIco();
        String sign = oldUserInfo.getSign();
        Byte power = oldUserInfo.getPower();

        if(power<0){
            return -1;
        }
        oldUserInfo.setPower(power);
        return userDao.modifyUserByName(userName,password,userIco,sign,power);
    }

    /**
     * 发送手机短信验证码
     * @param phoneNum
     * @throws IOException
     */
    public void sendMessage(String phoneNum) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */

        String checkSum = getCheckSum(APP_SECRET+NONCE+curTime);


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
        HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */
        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));

    }

    private String getCheckSum(String data){
        char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        int len;
        byte[] newData;
        if (data == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha1");
            messageDigest.update(data.getBytes());
            newData = messageDigest.digest();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        len = newData.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(newData[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[newData[j] & 0x0f]);
        }
        return buf.toString();
    }
}

