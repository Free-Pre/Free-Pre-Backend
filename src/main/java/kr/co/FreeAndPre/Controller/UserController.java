package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Dto.UserDto;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.Service.UserService;
import kr.co.FreeAndPre.response.BaseException;
import kr.co.FreeAndPre.response.BaseResponse;
import kr.co.FreeAndPre.response.BaseResponseStatus;
import org.apache.tomcat.jni.User;
import org.springframework.web.bind.annotation.*;

import static kr.co.FreeAndPre.response.BaseResponseStatus.*;

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
    public BaseResponse<Boolean> getUserExist(@PathVariable("userEmail") String userEmail) {
        Boolean exist = userService.getUserExist(userEmail);

        if (exist == true)
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
    public BaseResponse<String> insertUser(@RequestBody UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getNickname() == null || userDto.getFirst_period() == null)
            return new BaseResponse<>(REQUEST_ERROR);

        if (userDto.getFirst_period() == true) {
            //free 버전
            userService.insertFreeUser(userDto);
        } else {
            //pre 버전
            userService.insertPreUser(userDto);
        }

        return new BaseResponse<>(userDto.getEmail() + "의 회원가입이 완료되었습니다.");
    }

    /*
   3. 회원 닉네임 변경
    */
    @ResponseBody
    @PatchMapping("/nickname/{userEmail}")
    public BaseResponse<String> modifyUserNickname(@PathVariable("userEmail") String userEmail, @RequestBody UserDto userDto) {
        if (userDto.getNickname() == null) {
            return new BaseResponse<>(NICKNAME_BLANCK);
        }

        userService.modifyUserNickname(userEmail, userDto);

        return new BaseResponse<>("'" + userDto.getNickname() + "'" + "(으)로 닉네임 수정에 성공하였습니다.");
    }

    /*
    4. 버전 변경
     */
    @ResponseBody
    @PatchMapping("/version/{userEmail}")
    public BaseResponse<String> modifyUserVersion(@PathVariable("userEmail") String userEmail, @RequestBody UserDto userDto) {
        if (userDto.getFirst_period() == null) {
            return new BaseResponse<>(FIRST_PERIOD_BLANCK);
        }

        //pre -> free
        if (userDto.getFirst_period() == true)
            userService.pretofree(userEmail, userDto);
        //free -> pre
        else
            userService.freetopre(userEmail, userDto);

        return new BaseResponse<>("버전 변경에 성공하였습니다.");
    }

    /*
    5. 회원 탈퇴
     */
    @ResponseBody
    @DeleteMapping("/delete/{userEmail}")
    public BaseResponse<String> deleteUser(@PathVariable("userEmail") String userEmail) {

        if(!userService.getUserExist(userEmail)){
            return new BaseResponse<>(BaseResponseStatus.NO_USER);
        }

        userService.deleteUser(userEmail);

        return new BaseResponse<>("회원 탈퇴에 성공하였습니다.");
    }
}
