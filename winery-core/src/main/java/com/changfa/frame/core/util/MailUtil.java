package com.changfa.frame.core.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtil
{
    /**
     * 邮箱会话
     */
    private transient Session _session;

    /**
     * 创建一个邮件工具类的实例。
     * 
     * @param host
     *            发送邮件服务器的地址。
     * @param port
     *            发送邮件服务器的端口。
     * @param username
     *            用户名。
     * @param password
     *            密码。
     */
    public MailUtil(String host, int port, String username, String password)
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        props.put("mail.smtp.host", host);

        // 验证信息
        MailAuthenticator authenticator = new MailAuthenticator(username, password);

        _session = Session.getInstance(props, authenticator);
    }

    /**
     * 发送普通邮件。
     * 
     * @param from
     *            发件邮箱
     * @param to
     *            收件邮箱
     * @param subject
     *            邮件主题
     * @param text
     *            邮件正文
     * @throws MessagingException
     */
    public void sendSimpleMail(String from, int port, String to, String subject, String text)
    {
        try
        {
            // 创建mime类型邮件
            final MimeMessage message = new MimeMessage(_session);

            // 设置发信人
            message.setFrom(new InternetAddress(from));

            // 设置收件人
            message.setRecipient(RecipientType.TO, new InternetAddress(to));

            // 设置主题
            message.setSubject(subject);

            // 设置邮件内容
            message.setText(text, "charset=utf-8");

            // 发送
            Transport.send(message);
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件。
     * 
     * @param from
     *            发件邮箱
     * @param to
     *            收件邮箱
     * @param subject
     *            邮件主题
     * @param text
     *            邮件正文
     * @throws MessagingException
     */
    public void sendHtmlMail(String from, String to, String subject, String text)
    {
        try
        {
            // 创建mime类型邮件
            final MimeMessage message = new MimeMessage(_session);

            // 设置发信人
            message.setFrom(new InternetAddress(from));

            // 设置收件人
            message.setRecipient(RecipientType.TO, new InternetAddress(to));

            // 设置主题
            message.setSubject(subject);

            // 设置邮件内容
            message.setContent(text, "text/html;charset=utf-8");

            // 发送
            Transport.send(message);
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }
}
