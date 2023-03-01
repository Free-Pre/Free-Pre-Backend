package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Dto.UserDto;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.Service.UserService;
import kr.co.FreeAndPre.response.BaseException;
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
    public BaseResponse<Boolean> getUserExist(@PathVariable("userEmail") String userEmail) {
        Boolean exist = userService.getUserExist(userEmail);
        UserDto userDto = null;

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
    public BaseResponse<String> insertPeriod(@RequestBody UserDto userDto) {

        int userSuccess = 0;

        if (userDto.getEmail() == null || userDto.getNickname() == null || userDto.getFirst_period() == null)
            return new BaseResponse<>("입력값을 확인해주세요.");

        if (userDto.getFirst_period() == true) {
            //free 버전
            userSuccess = userService.insertFreeUser(userDto);
        } else {
            //pre 버전
            userSuccess = userService.insertPreUser(userDto);
        }

        if (userSuccess == 1)
            return new BaseResponse<>("회원가입에 성공하였습니다.");
        else
            return new BaseResponse<>("회원가입에 실패하였습니다.");
    }

    /*
   3. 회원 닉네임 변경
    */
    @ResponseBody
    @PatchMapping("/nickname/{userEmail}")
    public BaseResponse<String> modifyUserNickname(@PathVariable("userEmail") String userEmail, @RequestBody UserDto userDto) {
        if (userDto.getNickname() == null) {
            return new BaseResponse<>("닉네임을 입력하세요.");
        }

        int userSuccess = userService.modifyUserNickname(userEmail, userDto);

        if (userSuccess == 1)
            return new BaseResponse<>("닉네임 수정에 성공하였습니다.");
        else
            return new BaseResponse<>("닉네임 수정에 실패하였습니다.");
    }

    /*
    4. 버전 변경
     */
    @ResponseBody
    @PatchMapping("/version/{userEmail}")
    public BaseResponse<String> modifyUserVersion(@PathVariable("userEmail") String userEmail, @RequestBody UserDto userDto) {
        if (userDto.getFirst_period() == null) {
            return new BaseResponse<>("초경여부를 입력하세요.");
        }

        int userSuccess = 0;

        //pre -> free
        if (userDto.getFirst_period() == true)
            userSuccess = userService.pretofree(userEmail, userDto);
        //free -> pre
        else
            userSuccess = userService.freetopre(userEmail, userDto);

        if (userSuccess == 1)
            return new BaseResponse<>("버전 변경에 성공하였습니다.");
        else
            return new BaseResponse<>("버전 변경에 실패하였습니다.");
    }

    /*
    5. 회원 탈퇴
     */
    @ResponseBody
    @DeleteMapping("/delete/{userEmail}")
    public BaseResponse<String> deleteUser(@PathVariable("userEmail") String userEmail) {

        int userSuccess = userService.deleteUser(userEmail);

        if (userSuccess == 1)
            return new BaseResponse<>("회원 탈퇴에 성공하였습니다.");
        else
            return new BaseResponse<>("회원 탈퇴에 실패하였습니다.");
    }
}
