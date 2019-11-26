package com.exam.pay.bean;

public class PayRequestInfo {

    /**
     * IsSuccess : true
     * Data : {"body":"测试","charset":"UTF-8","mch_create_ip":"127.0.0.1","mch_id":"118520000213","nonce_str":"43147c6cbf3b4bd2ad02842bdaae5030","notify_url":"http://203.174.52.78:1123/AliNotifyurl/notify_url","out_trade_no":"testtes1t","pay_info":"_input_charset=\"utf-8\"&body=\"测试\"&currency=\"HKD\"&forex_biz=\"FP\"&it_b_pay=\"2h\"&notify_url=\"https://notify.wepayez.com/pay/notify/pay.alipay.app.intl@118520000213201911261087712269\"&out_trade_no=\"118520000213201911261087712269\"&partner=\"2088331130453553\"&payment_type=\"1\"&product_code=\"NEW_WAP_OVERSEAS_SELLER\"&secondary_merchant_id=\"118520000213\"&secondary_merchant_industry=\"5499\"&secondary_merchant_name=\"測試Online-2號(fresh)\"&seller_id=\"2088331130453553\"&service=\"mobile.securitypay.pay\"&subject=\"测试\"&total_fee=\"0.01\"&sign=\"CfdwpcGnB633%2F3pLDTp27vCUwuTEDbYMRLXu71qby14LqvJly3FakD8Q83p2P%2Big9NcH697S1%2BeXYWEtPNGC0G053XQK%2BlGspGbA45Y0joyxmOtXbb6X1uUd%2FSnwE%2F8IlU2pFo5kN9lQ1JMuntmybj1%2B51HJivntEprihra%2FEjI8Tfz8YQRn%2FT8QVZcXzWc%2FWwif5iDjZU1rHTfWoX2zwP%2B5Bq906YeomWs0cttyBl5p4Qe%2FKFmD5DIA8y1Ux36b9nrCVkYxxDg72Wm7EIhWJJCWBgHKcuz3BlqTYYxpyTmqM9IP3NeApB7hp%2BR0KE6IoSu0RaJqjgNze%2FPTB%2Fmt7w%3D%3D\"&sign_type=\"RSA\"","result_code":"0","service":"pay.alipay.app.intl","sign":"10EF3E1CF6BF5B4BE6DE07D54E34545B","sign_type":"MD5","status":"0","total_fee":1,"version":"2.0"}
     */

    private boolean IsSuccess;
    private DataBean Data;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * body : 测试
         * charset : UTF-8
         * mch_create_ip : 127.0.0.1
         * mch_id : 118520000213
         * nonce_str : 43147c6cbf3b4bd2ad02842bdaae5030
         * notify_url : http://203.174.52.78:1123/AliNotifyurl/notify_url
         * out_trade_no : testtes1t
         * pay_info : _input_charset="utf-8"&body="测试"&currency="HKD"&forex_biz="FP"&it_b_pay="2h"&notify_url="https://notify.wepayez.com/pay/notify/pay.alipay.app.intl@118520000213201911261087712269"&out_trade_no="118520000213201911261087712269"&partner="2088331130453553"&payment_type="1"&product_code="NEW_WAP_OVERSEAS_SELLER"&secondary_merchant_id="118520000213"&secondary_merchant_industry="5499"&secondary_merchant_name="測試Online-2號(fresh)"&seller_id="2088331130453553"&service="mobile.securitypay.pay"&subject="测试"&total_fee="0.01"&sign="CfdwpcGnB633%2F3pLDTp27vCUwuTEDbYMRLXu71qby14LqvJly3FakD8Q83p2P%2Big9NcH697S1%2BeXYWEtPNGC0G053XQK%2BlGspGbA45Y0joyxmOtXbb6X1uUd%2FSnwE%2F8IlU2pFo5kN9lQ1JMuntmybj1%2B51HJivntEprihra%2FEjI8Tfz8YQRn%2FT8QVZcXzWc%2FWwif5iDjZU1rHTfWoX2zwP%2B5Bq906YeomWs0cttyBl5p4Qe%2FKFmD5DIA8y1Ux36b9nrCVkYxxDg72Wm7EIhWJJCWBgHKcuz3BlqTYYxpyTmqM9IP3NeApB7hp%2BR0KE6IoSu0RaJqjgNze%2FPTB%2Fmt7w%3D%3D"&sign_type="RSA"
         * result_code : 0
         * service : pay.alipay.app.intl
         * sign : 10EF3E1CF6BF5B4BE6DE07D54E34545B
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
        private String err_code;
        private String err_msg;

        public String getErr_code() {
            return err_code;
        }

        public void setErr_code(String err_code) {
            this.err_code = err_code;
        }

        public String getErr_msg() {
            return err_msg;
        }

        public void setErr_msg(String err_msg) {
            this.err_msg = err_msg;
        }

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
}

