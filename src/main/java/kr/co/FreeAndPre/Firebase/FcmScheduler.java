package kr.co.FreeAndPre.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

import static kr.co.FreeAndPre.Firebase.FcmMessage.CHANGE_TIME;

@Slf4j
@Service
@PropertySource("classpath:app.properties")
public class FcmScheduler {

    String fireBaseCreateScoped= "https://www.googleapis.com/auth/firebase.messaging";

    private FirebaseMessaging instance;

    @PostConstruct
    public void firebaseSetting() throws IOException {
//        firebase 접속
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("/firebase/firebase_service_key.json").getInputStream())
                .createScoped((Arrays.asList(fireBaseCreateScoped)));
        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
        this.instance = FirebaseMessaging.getInstance(app);

    }

    public void pushChangeAlarm() throws FirebaseMessagingException {
        log.info("change time");
        pushAlarm(CHANGE_TIME);
    }

    private void pushAlarm(FcmMessage data) throws FirebaseMessagingException{
        Message message = getMessage(data);
        sendMessage(message);
    }

    private Message getMessage(FcmMessage data) {
        Notification notification = Notification.builder().setTitle(data.getTitle()).setBody(data.getBody()).build();
        Message.Builder builder = Message.builder();
        Message message = builder.setNotification(notification).build();
        return message;
    }

    public String sendMessage(Message message) throws FirebaseMessagingException{
        return this.instance.send(message);
    }
}

