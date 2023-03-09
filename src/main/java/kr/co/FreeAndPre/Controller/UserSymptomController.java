package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.UserSymptomDto;
import kr.co.FreeAndPre.Service.UserSymptomService;
import kr.co.FreeAndPre.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("freepre/userSymptom")
public class UserSymptomController {

    private UserSymptomService userSymptomService;
    public UserSymptomController(UserSymptomService userSymptomService) { this.userSymptomService = userSymptomService; }


//    1. 증상 가져오기 / 이메일, 날짜 둘 다 받아와야 해
    @ResponseBody
    @GetMapping ("/{email}/{date}")
    public BaseResponse<UserSymptomDto> getUserSymptom (@PathVariable("email") String email, @PathVariable("date") String date){
        UserSymptomDto userSymptomDto = userSymptomService.getUserSymptom(email,date);

        return  new BaseResponse<>(userSymptomDto);
    }




}
