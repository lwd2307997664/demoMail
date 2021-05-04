package com.yangxf.demoMail;

import com.yangxf.demoMail.entity.User;
import com.yangxf.demoMail.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@SpringBootTest
@RunWith(SpringRunner.class)
class DemoMailApplicationTests {

    @Autowired
    MailService mailService;

    @Test
    void sendSimpleMail() {
        mailService.sendSimpleMail("2307997664@qq.com",
                "lin.wd@neusoft.com",
                "15942242540@163.com",
                "测试发送邮件主题",
                "测试发送邮件内容");
    }

    @Test
    void sendAttachFileMail() {
        mailService.sendAttachFileMail("2307997664@qq.com",
                "lin.wd@neusoft.com",
                "15942242540@163.com",
                "测试发送邮件主题",
                "测试发送邮件内容",new File("F:\\Downloads\\测试附件.rar"));
    }

    @Test
    void sendAttachFileMoreOneMail() {
        File[] files=new File[]{
                new File("F:\\Downloads\\测试附件.rar"),
                new File("F:\\Downloads\\测试附件2.rar"),
                new File("F:\\Downloads\\测试附件3.rar")};

        mailService.sendAttachFileMoreOneMail("2307997664@qq.com",
                "lin.wd@neusoft.com",
                "15942242540@163.com",
                "测试发送邮件主题",
                "测试发送邮件内容",files);
    }

    @Test
    void sendMailWithImg() {
        String[] srcPath=new String[]{"F:\\Downloads\\11.png","F:\\Downloads\\22.png"};
        String[] resIds=new String[]{"i1","i2"};

        mailService.sendMailWithImg("2307997664@qq.com",
                "lin.wd@neusoft.com",
                "15942242540@163.com",
                "测试发送邮件主题(图片)",
                "<div>hello,这是一个发送图片的邮件</div>\n" +
                        "图片1：\n" +
                        "<div><img src='cid:i1'/></div>\n" +
                        "图片2:\n" +
                        "<div><img src='cid:i2'/></div>\n",
                srcPath,resIds);
    }


    @Test
    void sendHtmlMail() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        ClassLoader classLoader=DemoMailApplication.class.getClassLoader();
        configuration.setClassLoaderForTemplateLoading(classLoader, "ftl");
        Template template=configuration.getTemplate("mailTemplate.ftl");
        StringWriter mail=new StringWriter();
        User user=new User("linwd","男");
        template.process(user,mail);
        mailService.sendHtmlMail("2307997664@qq.com",
                "lin.wd@neusoft.com",
                null,
                "测试发送邮件主题",
                mail.toString());
    }

    @Autowired
    TemplateEngine templateEngine;
    @Test
    void sendHtmlMailForThymeleaf() throws IOException, TemplateException {

        Context context=new Context();
        context.setVariable("username","linwd");
        String mail=templateEngine.process("mailTemplateForThymeleaf.html",context);
        mailService.sendHtmlMail("2307997664@qq.com",
                "lin.wd@neusoft.com",
                null,
                "测试发送邮件主题",
                mail);
    }


}
