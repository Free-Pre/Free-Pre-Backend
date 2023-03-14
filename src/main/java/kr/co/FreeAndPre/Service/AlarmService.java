package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.AlarmDao;
import kr.co.FreeAndPre.Dto.AlarmDto;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

    public AlarmDto getAlarm(String email){
        AlarmDto alarmDto = AlarmDao.getInstance().getAlarm(email);

        return alarmDto;
    }


}
