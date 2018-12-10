/*
package com.news.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

*/
/**
 * Created with IntelliJ IDEA.
 * Created by:  Vivian
 * Date: 2018/8/11 21:34
 *//*

@RestController
@RequestMapping("alipay")
public class AlipayController {
    @Autowired
    AlipayClientFactory factory;
    @RequestMapping("/paybyali")
    public String payByAlipay(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        AlipayClient client = factory.getAlipayClient();
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(AlipayConfig.RETURNURL.getValues());
        payRequest.setNotifyUrl(AlipayConfig.NOTIFYURL.getValues());

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填
        String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        //与支付宝签约的商品代码，目前仅支持FAST_INSTANT_TRADE_PAY
        String product_code = "FAST_INSTANT_TRADE_PAY";
        //订单名称，必填
        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        //商品描述，可空
        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

        payRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                +"\"product_code\":\""+ product_code +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        try {
            AlipayTradePagePayResponse payResponse = client.execute(payRequest);

            return payResponse.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/notify")
    public String getAlipayNotify(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY.getValues(), AlipayConfig.CHARSET.getValues(), AlipayConfig.SIGN_TYPE.getValues()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	*/
/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*//*

        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }
            return "SUCCESS";
        } else {//验证失败
            return "FAIL";
            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
    }

    @RequestMapping("/refund")
    public String ailpayRefund(HttpServletRequest request) throws UnsupportedEncodingException {
        //退款请求判断逻辑
        if(true)
        {
            AlipayClient client = factory.getAlipayClient();
            AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
            //商户订单号，商户网站订单系统中唯一订单号
            String out_trade_no = new String(request.getParameter("WIDTRout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("WIDTRtrade_no").getBytes("ISO-8859-1"),"UTF-8");
            //请二选一设置
            //需要退款的金额，该金额不能大于订单金额，必填
            String refund_amount = new String(request.getParameter("WIDTRrefund_amount").getBytes("ISO-8859-1"),"UTF-8");
            //退款的原因说明
            String refund_reason = new String(request.getParameter("WIDTRrefund_reason").getBytes("ISO-8859-1"),"UTF-8");
            //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
            String out_request_no = new String(request.getParameter("WIDTRout_request_no").getBytes("ISO-8859-1"),"UTF-8");

            refundRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                    + "\"trade_no\":\""+ trade_no +"\","
                    + "\"refund_amount\":\""+ refund_amount +"\","
                    + "\"refund_reason\":\""+ refund_reason +"\","
                    + "\"out_request_no\":\""+ out_request_no +"\"}");

            try {
                AlipayTradeRefundResponse refundRespons = client.execute(refundRequest);
                if(refundRespons.getRefundFee().equals(refund_amount)) {
                    return "退款成功";
                }else return "退款数量异常";
            } catch (AlipayApiException e) {
                e.printStackTrace();
                return "退款异常";
            }

        }else {

            return "不满足退款条件";
        }
    }
}
*/
