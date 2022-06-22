package geon.hee.tobyspring.service;

import geon.hee.tobyspring.domain.User;
import geon.hee.tobyspring.repository.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class SendMailUserLevelUpgradePolicy implements UserLevelUpgradePolicy {

    private final UserDao userDao;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.password}")
    private String password;

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEMail(user);
    }

    private void sendUpgradeEMail(User user) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            // 송신자 메일주소
            message.setFrom(new InternetAddress(sender));

            // 수신자 메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

            // 메일 제목
            message.setSubject("Upgrade 안내");

            // 메일 내용
            message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드되었습니다.");

            // 메일 전송
            Transport.send(message);
            log.info("success Message Send : {}", user.getName());
        } catch (MessagingException e) {
            log.error("failed Message Send : {}", user.getName());
            throw new RuntimeException(e);
        }
    }
}
