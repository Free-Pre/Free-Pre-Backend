package kr.co.FreeAndPre.Firebase;

import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FCMInitializer {

    @PostConstruct
    public void initialize() throws IOException {

        FileInputStream refreshToken = new FileInputStream("/resources/firebase_service_key.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setProjectId("free-pre-376814")
                .build();

        FirebaseApp.initializeApp(options);
    }


}
