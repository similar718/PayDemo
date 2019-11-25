package com.exam.pay.bean;

public class PayRequestInfo {

    /**
     * body : 测试
     * charset : UTF-8
     * mch_create_ip : 127.0.0.1
     * mch_id : 118520000213
     * nonce_str : fd53259281a74b09b1ee48f213f8e52b
     * notify_url : http://localhost:54662/AliAppPay/AliPayNotifyUrl
     * out_trade_no : testtest
     * pay_info : _input_charset="utf-8"&body="测试"&currency="HKD"&forex_biz="FP"&it_b_pay="2h"&notify_url="https://notify.wepayez.com/pay/notify/pay.alipay.app.intl@118520000213201911251187670465"&out_trade_no="118520000213201911251187670465"&partner="2088331130453553"&payment_type="1"&product_code="NEW_WAP_OVERSEAS_SELLER"&secondary_merchant_id="118520000213"&secondary_merchant_industry="5499"&secondary_merchant_name="測試Online-2號(fresh)"&seller_id="2088331130453553"&service="mobile.securitypay.pay"&subject="测试"&total_fee="0.01"&sign="OCHwi9KUW5R3y9Dwj96pZE5TNxl8ppZBTFDyUa9N7b%2BzaimyoxSQ47wAvNDqFuNBi0HFRst9fCIkJNr5oowLqw9nUpcP8BFR8xVbG%2FgojEJEt2OOXWQ%2F%2FcnHsEO2Yf89%2FB9nX7hers8hLkXI7kON0j5Y9NZEBxZoUfLn%2BBKj9HyIl9h8WIiW02kcXQqG9X%2BllnLFQ1xzzG0ejs1MOoyAi67k1nRzkkbhOcNEGgGa0OuwKYxkaAApUUSIoM3RX9k%2B8jkKROig0hGLEBTBvJF0mtYmSLh6o0gTYeWBnIaDYg%2FZqhhzrvbXVcXrp8S1wH6rtNp9bMZYN00W2f3UKfFAlQ%3D%3D"&sign_type="RSA"
     * result_code : 0
     * service : pay.alipay.app.intl
     * sign : 616018A85CB5C05EC3C89DAE8F1DA9F8
     * sign_type : MD5
     * status : 0
     * total_fee : 1
     * version : 2.0
     */

    private String body;
    private String charset;
    private String mch_create_ip;
    private String mch_id;
    private String nonce_str;
    private String notify_url;
    private String out_trade_no;
    private String pay_info;
    private String result_code;
    private String service;
    private String sign;
    private String sign_type;
    private String status;
    private int total_fee;
    private String version;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getMch_create_ip() {
        return mch_create_ip;
    }

    public void setMch_create_ip(String mch_create_ip) {
        this.mch_create_ip = mch_create_ip;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

