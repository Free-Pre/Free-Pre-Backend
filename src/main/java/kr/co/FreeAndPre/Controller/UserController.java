package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.Service.UserService;
import kr.co.FreeAndPre.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
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
         return new BaseResponse<>(exist);
     }
}
