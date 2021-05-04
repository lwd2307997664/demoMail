/**
 * FileName: MailService
 * Author:   linwd
 * Date:     2021/5/3 16:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yangxf.demoMail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 〈简单发送〉<br>
 * 〈〉
 *
 * @author linwd
 * @create 2021/5/3
 * @since 1.0.0
 */
@Component
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    /**
     * 发送简单的邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String from, String to, String cc, String subject, String content) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setFrom(from);
        sm.setTo(to);
        sm.setCc(cc);
        sm.setSubject(subject);
        sm.setText(content);
        javaMailSender.send(sm);
    }


    /**
     * 发送带附件的邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param subject
     * @param content
     */
    public void sendAttachFileMail(String from, String to, String cc, String subject, String content, File file) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setText(content);
            helper.setSubject(subject);
            helper.addAttachment(file.getName(), file);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带多个附件的邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param subject
     * @param content
     */
    public void sendAttachFileMoreOneMail(String from, String to, String cc, String subject, String content, File[] files) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setText(content);
            helper.setSubject(subject);
            for (File file : files) {
                helper.addAttachment(file.getName(), file);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带图片的邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param subject
     * @param content
     */
    public void sendMailWithImg(String from, String to, String cc, String subject,
                                String content, String[] srcPath, String[] resIds) {
        if (srcPath.length != resIds.length) {
            System.out.println("发送失败");
            return;
        }
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setText(content,true);//是否是html内容
            helper.setSubject(subject);
            for (int i = 0; i < srcPath.length; i++) {
                FileSystemResource res = new FileSystemResource(new File(srcPath[i]));
                helper.addInline(resIds[i], res);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送失败");
        }
    }

    /**
     * 发送HTML模板的邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String from, String to, String cc, String subject,
                                String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            if(cc!=null){
                helper.setCc(cc);
            }
            helper.setText(content,true);//是否是html内容
            helper.setSubject(subject);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送失败");
        }
    }
}
