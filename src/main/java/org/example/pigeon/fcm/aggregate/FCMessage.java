package org.example.pigeon.fcm.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class FCMessage {

    @Value("${fcm.token:11111}")
    private String authKeyFcm = "FCM에서 가입 후 받는 키값";
    private String apiUrlFcm = "test";

    public void pushFCM() throws IOException {
//        String _title = "앱 알림";
//        String _body = "푸쉬메시지가 도착했습니다.";
//        String _actionType = "new";
//        String _code = "test";
//        //String _token = "/topics/ALL"; // 전체
//
//        // 모바일기기에서 얻음
//        String _token = "APK 다운로드 할 때, 얻게 되는 TOKEN"; // 개인
//
//
//        final String apiKey = authKeyFcm;
//        URL url = new URL(apiUrlFcm);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoOutput(true);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json");
//        conn.setRequestProperty("Authorization", "key=" + apiKey);
//
//        conn.setDoOutput(true);
//
//
//        JSONObject json = new JSONObject();
//        JSONObject notification = new JSONObject();
//
//        notification.put("title", _title);
//        notification.put("body", _body);
//
//        json.put("notification", notification);
//        json.put("to", _token);
//
//        String sendMsg = json.toString();
//
//        OutputStream os = conn.getOutputStream();
//
//        // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
//        //os.write(input.getBytes("UTF-8"));
//        os.write(sendMsg.getBytes("UTF-8"));
//        os.flush();
//        os.close();
//
//        int responseCode = conn.getResponseCode();
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//        // print result
    }
}
