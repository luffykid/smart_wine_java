package com.changfa.frame.core.weChat.util;

import com.changfa.frame.core.weChat.res.PaymentResult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

/**
 * 微信响应消息工具类
 *
 * @author wyy
 * @date 2019-08-30
 */
public class ResMessageUtil {

    /**
     * 返回给微信的支付结果转换成xml
     *
     * @param paymentResult 返回给微信的结果
     * @return
     */
    public static String paymentResultToXml(PaymentResult paymentResult) {
        xstream.alias("xml", paymentResult.getClass());
        return xstream.toXML(paymentResult);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                public String encodeNode(String name) {
                    return name;
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });


}
