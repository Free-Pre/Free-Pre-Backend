package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.UserSymptomDao;
import kr.co.FreeAndPre.Dto.UserSymptomDto;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserSymptomService {

//    1. 증상 가져오기 (email, date 이용)
    public UserSymptomDto getUserSymptom (String email, String date){
        UserSymptomDto userSymptomDto = UserSymptomDao.getInstance().getUserSymptom(email, date);

        return userSymptomDto;
    }

    public int insertUserSymptom(UserSymptomDto userSymptomDto){
        int result = UserSymptomDao.getInstance().insertUserSymptom(userSymptomDto);

        return result;
    }

    public void editUserSymptom(String email, String date, UserSymptomDto userSymptomDto){
        UserSymptomDao.getInstance().editUserSymptom(email, date, userSymptomDto);
    }

}
