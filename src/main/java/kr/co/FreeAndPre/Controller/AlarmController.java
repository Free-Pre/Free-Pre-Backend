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

    //    2. 알람 등록
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> saveAlarm(@RequestBody AlarmDto alarmDto){
        int alarmSuccess = alarmService.saveAlarm(alarmDto);
        if (alarmSuccess == 1)
            return new BaseResponse<>("알람 저장에 성공하였습니다.");
        else
            return new BaseResponse<>("알람 저장에 실패하였습니다.");
    }

//    3. 알람 수정
    @ResponseBody
    @PatchMapping("edit/{email}")
    public BaseResponse<String> editAlarm(@PathVariable("email") String email, @RequestBody AlarmDto alarmDto){
        alarmService.editAlarm(email, alarmDto);

        return new BaseResponse<>("알람 수정에 성공하였습니다.");
    }
}
