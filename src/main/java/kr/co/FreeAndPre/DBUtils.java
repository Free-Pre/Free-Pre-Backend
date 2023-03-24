package kr.co.FreeAndPre;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
    public static Connection getConnection() {
        Connection conn = null; // 커넥션을 담을 변수 생성

        String driver = "com.mysql.cj.jdbc.Driver";

        String url = "jdbc:mysql://34.64.190.83:3306/FreePreDB?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul";
//        String url = "jdbc:mysql://127.0.0.1:3306/FreePreDB";

        String user = "root"; // 데이터 베이스 사용자의 접속 아이디 입력

        String password = "root!"; // 데이터 베이스 사용자의 접속 비밀번호 입력
//        String password = "rlacodms01";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("데이터 베이스 연결 실패");
        }return conn;
    }
}
