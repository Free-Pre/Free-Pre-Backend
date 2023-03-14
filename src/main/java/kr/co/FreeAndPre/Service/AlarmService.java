package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.AlarmDao;
import kr.co.FreeAndPre.Dto.AlarmDto;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

//    알람 조회
    public AlarmDto getAlarm(String email){
        AlarmDto alarmDto = AlarmDao.getInstance().getAlarm(email);

        return alarmDto;
    }

//   알람 저장
    public int saveAlarm (AlarmDto alarmDto){
        int result = AlarmDao.getInstance().saveAlarm(alarmDto);

        return result;
    }

//    알람 수정
    public void editAlarm(String email, AlarmDto alarmDto){
        AlarmDao.getInstance().editAlarm(email,alarmDto);
    }

}
