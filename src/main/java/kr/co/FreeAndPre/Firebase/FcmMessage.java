package kr.co.FreeAndPre.Firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.aspectj.bridge.Message;

import java.security.MessageDigest;

@Getter
@AllArgsConstructor
public enum FcmMessage {
    CHANGE_TIME("Change Time","It's time to change sanitary products!");

    String title;
    String body;
}
