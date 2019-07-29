package com.changfa.frame.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Title: Constant
 * Description: 常量类
 *
 * @author limin@chfatech.com
 */
public final class  Constant {


    public static final Integer wineryId = 4;
    //支付宝密钥
    public static final String PAY_RSA2 = "MIMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRj1X/xrxRJzuI5bvY+cd+gPyOdb0yCnNBImNdpp04/1aB1VmE+IOHC4DF6X//XmypEEGC+YCczDPbq4/MQjt/mzLk22Cr6MBqwlbCoi3PBoFp84wc0J0+dWBLDWitmQVKZpfYbp8uxfuY1r1RTTnW5vc1f378vo5KBgGqFDT1qZcgyb2zXcn89HzLOptMUP5oijrGmf8tZFWaQFrKEsXWHwNIKkSY7qk1uhbHU4ZyTpYwst5L4yH5d6vAFHogdaIrPNPAktuOL7ADyWs2syPhxH6k05QEtsLMqjqjreiobjA6LhVIGkgOz5YErdUtokBdn17X5Bx4onazF4Dt7A4VAgMBAAECggEBAI15I9vae9PgbtPKfb4QXx4MHewUcVN+prCKzMsMEkmAmsLL6xr7M1MBEiLJpNc+HhxfqVy0RZWgP1LlN3kOcqe+30NiGV5cdRAOXKcFW8KYjxW8WdTzybEYAgRQPwpi7Kp4dmMseVjLopLVO+IWTf4aM/FyPqlGSCOYHOwCgcq1klHLCRFaNYZURVt+vFkyN3q+WbxSKRbX0BvWqMradTiHaGaQcnIzb9MYkVb7k/hWVpcMtXV6KE8aIDQ9UlgbhsABgLBHrcdjZR1WM1fPPpxyuMgGpTtIm8fOuMzDv+6TiHRR22UIm+q+bZ3pzpIsxD1CM3QGEhb1v64teM550aECgYEA5VvMw5VQXRtwQQgOKaQgqVTXheGaSfp4k2Y3GBrkFrLZiZD8dYZfeNTLy6BfXR03fWa8dV1lxq8hc9nr1ZYrTjtosEpxXZqZx0XvaCvAEhmRQWR/bRW0d0Up27wfFQpRv/JM65GKCq5iclX8ARSNDcZciLyYZLYtSPNVdiXT9J0CgYEAoneza9zDIUwDeBW9eai9vC5lrAeltkvMkjalPIVlQRKfPqbrJzw8s81+yK0eBRKz4y57Mk7pZZ9MGvHLtmIJSdzuIHTCRNlRB26WSuUYjqiRrnTMp09ICXZ9q+g6FSeTd0tpOGEGxev1ITismAJzRwHmcrcVTslXzRJXIMUZ+dkCgYEAjwV+m1upoFWy2yqNwlOxdkYMm1TCJG2G8MtlgaeLIchnNIxvkU24jR2M865OqT7F0nHVBQ63n3ynzJlQoYCiAoezUXMqXCdCy2uFzZyyidbdqDxl5X0KavF7F6WocDNeJOaQJrk7rktt4kRR/85z4uoV28JW+T5M4Ne0LBeMo1UCgYBwvvDcYY9og//EThH3Y4qIFQqpn4T4RRHqRdHVdh0WU33/VIbL65mUjX72dGzoyFndxlvoRariwvFloXblUpNvlsoJ6/0FGmqI5c2Wm/W2ynSOxK9hTDuJlZkQu0/Tbmt/1e1nGq5Am8a5D0N3Gfw1wrPR2AwJaOBth9S/AQlNwQKBgHXaiBwWg01NaIi9YjfkWMUFJsAggrve//q8vrUImGBLFQykyPy+TaiZYXaQvBMbKfn1wQlUPldShaRxoUA8FzVHUB9FXqwm+fwH6Ib+TV4ocbT7L731p73bFGXl7zFX3yagrql0Najl74Yd0XQlHwJ8Vr7Q5xMkYrYXx+unSmH2WE";

    //酒庄正式
    private static final String SERVER_IP_PORT = "42.159.9.46";


    public static final String WECHAT_APP = "https://xdq.chfatech.com:8123";

    //用户图片路径
    public static final String XINDEQI_ICON_PATH = "http://" + SERVER_IP_PORT;

    //小数点后两位的decimal格式
    public static final String DECIMAL_FORMAT_TWO = "0.00";


    //返回格式化的Decimal类型，格式0.00
    public static BigDecimal decimalFormat(BigDecimal decimal) {
        return new BigDecimal(new DecimalFormat(DECIMAL_FORMAT_TWO).format(decimal));
    }

    //返回格式化的Decimal类型，格式0.00
    public static BigDecimal decimalFormat(Double doub) {
        return new BigDecimal(doub).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    //----以下为夜间出行系统使用---------

    // todo 路径需要规划建立, 修改为相对路径，例如 ../../vimg/cert/, 相对部署包ROOT的路径，表示tomcat目录下cert_img 文件夹

}
