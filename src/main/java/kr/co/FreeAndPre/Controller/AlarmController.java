package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.AlarmDto;
import kr.co.FreeAndPre.Service.AlarmService;
import kr.co.FreeAndPre.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("freepre/alarm")
public class AlarmController {

    private AlarmService alarmService;
    public AlarmController(AlarmService alarmService){ this.alarmService =alarmService;  }

//    1. 알람 조회

    @ResponseBody
    @GetMapping("/{email}")
    public BaseResponse<AlarmDto> getAlarm (@PathVariable("email") String email){
        AlarmDto alarmDto = alarmService.getAlarm(email);

        return new BaseResponse<>(alarmDto);
    }


}
