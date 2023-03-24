package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.UserSymptomDto;
import kr.co.FreeAndPre.Service.UserSymptomService;
import kr.co.FreeAndPre.response.BaseResponse;
import kr.co.FreeAndPre.response.BaseResponseStatus;
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

        //        해당 날짜에 증상이 존재하지 않을 때
        if(!userSymptomService.getUserSymptomExist(email, date)){
            return new BaseResponse<>(BaseResponseStatus.NO_USERSYMPTOM);
        }
        return  new BaseResponse<>(userSymptomDto);
    }

    //    2. 증상 입력(추가)
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> insertUserSymptom(@RequestBody UserSymptomDto userSymptomDto){
        int userSymptomSuccess = userSymptomService.insertUserSymptom(userSymptomDto);

        if(userSymptomSuccess == 1)
            return new BaseResponse<>("몸 상태 입력에 성공하였습니다.");
        else
            return new BaseResponse<>("몸 상태 입력에 실패하였습니다.");
    }

//    3. 증상 수정
    @ResponseBody
    @PatchMapping("edit/{email}/{date}")
    public BaseResponse<String> editUserSymptom(@PathVariable("email") String email, @PathVariable ("date") String date, @RequestBody UserSymptomDto userSymptomDto){

        userSymptomService.editUserSymptom(email, date, userSymptomDto);

//        해당 날짜에 증상이 존재하지 않을 때
        if(!userSymptomService.getUserSymptomExist(email, date)){
            return new BaseResponse<>(BaseResponseStatus.NO_USERSYMPTOM);
        }

        return new BaseResponse<>("증상 정보 수정에 성공하였습니다.");
    }








}
