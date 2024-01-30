package chathealth.chathealth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    //인증번호 6자리
    public String createCode(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0; i<6; i++) {
            int idx = random.nextInt(3);

            switch (idx) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        return key.toString();
    }

    // 이메일 발신
    public String sendVerificationEmail(String email) throws MessagingException {
        String authcode = createCode();
        String setFrom = "baechanyongdev@gmail.com";
        String title = "[Chat Health] 본인인증을 위한 인증번호입니다.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(message, "UTF-8");

        // 메일 내용
        String msgOfEmail = "";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 Chat Health 입니다. </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>아래 코드를 10분 이내에 입력해주세요<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += authcode + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        mailHelper.setSubject(title);        // 제목 설정
        mailHelper.setFrom(setFrom);        // 보내는 사람 설정
        mailHelper.setTo(email);          // 받는 사람 설정
        mailHelper.setText(msgOfEmail, true);  //메일 내용 설정, HTML여부

        mailSender.send(message);
        return authcode;
    }

    public void sendNoticeRoleEmail(String email) throws MessagingException {
        String setFrom = "baechanyongdev@gmail.com";
        String title = "[Chat Health] 본인인증을 위한 인증번호입니다.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(message, "UTF-8");

        // 메일 내용
        String msgOfEmail = "";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 Chat Health 입니다.</h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>회원님의 권한이 변경되었습니다. 페이지에 접속하시어 확인해주시기 바랍니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "</div>";

        mailHelper.setSubject(title);        // 제목 설정
        mailHelper.setFrom(setFrom);        // 보내는 사람 설정
        mailHelper.setTo(email);          // 받는 사람 설정
        mailHelper.setText(msgOfEmail, true);  //메일 내용 설정, HTML여부

        mailSender.send(message);
    }



}
