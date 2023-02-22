package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Dto.UserDto;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.Service.UserService;
import kr.co.FreeAndPre.response.BaseResponse;
import org.apache.tomcat.jni.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/freepre/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

     /*
    1. 회원 존재 여부 확인하기
     */
     @ResponseBody
     @GetMapping("/{userEmail}")
     public BaseResponse<Boolean> getUserExist (@PathVariable("userEmail") String userEmail) {
         Boolean exist = userService.getUserExist(userEmail);
         UserDto userDto = null;

         if(exist == true)
            return new BaseResponse<>(exist);
         else {
             return new BaseResponse<>(exist);
         }

     }

     /*
    2. 회원 가입하기
     */
     @ResponseBody
     @PostMapping("")
     public BaseResponse<String> insertPeriod(@RequestBody UserDto userDto) {

         if(userDto.getEmail() == null || userDto.getNickname() == null || userDto.getFirst_period() == null ||
                 userDto.getNotice() == null || userDto.getPregnancy() == null)
             return new BaseResponse<>("입력값을 확인해주세요.");

         int userSuccess = userService.insertUser(userDto);

         if(userSuccess == 1)
             return new BaseResponse<>("회원가입에 성공하였습니다.");
         else
             return new BaseResponse<>("회원가입에 실패하였습니다.");
     }
}
