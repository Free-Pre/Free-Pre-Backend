package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.response.BaseResponse;
import kr.co.FreeAndPre.response.BaseResponseStatus;
import kr.co.FreeAndPre.Dto.HomePeriodDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.co.FreeAndPre.response.BaseResponseStatus.REQUEST_ERROR;

@RestController
@RequestMapping("/freepre/period")
public class PeriodController {

    private PeriodService periodService;

    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    /*
    1. 월경 정보 4개 가져오기
     */
    @ResponseBody
    @GetMapping("/{userEmail}")
    public BaseResponse<List<PeriodDto>> getPeriodInfoByEmail(@PathVariable("userEmail") String userEmail) {

        if(!periodService.getUserExist(userEmail)){
            return new BaseResponse<>(BaseResponseStatus.NO_USER);
        }

        List<PeriodDto> periodDto = periodService.getPeriodInfoByEmail(userEmail);

        if(periodDto.size() <= 0){
            return new BaseResponse<>(BaseResponseStatus.NO_PERIOD);
        }

        return new BaseResponse<>(periodDto);
    }

    /*
    2. 회원가입 직후 첫 월경일 정보 입력
     */
    @ResponseBody
    @PostMapping("/first")
    public BaseResponse<PeriodDto> insertFirstPeriod(@RequestBody PeriodDto periodDto) {

        if (periodDto.getEmail() == null || periodDto.getStart_date() == null || periodDto.getEnd_date() == null)
            return new BaseResponse<>(REQUEST_ERROR);

        if(!periodService.getUserExist(periodDto.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.NO_USER);
        }

        periodService.insertFirstPeriod(periodDto);

        return new BaseResponse<>(periodDto);
    }

    /*
    2-1. 월경 정보 입력하기
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PeriodDto> insertPeriod(@RequestBody PeriodDto periodDto) {

        if (periodDto.getEmail() == null || periodDto.getStart_date() == null || periodDto.getEnd_date() == null)
            return new BaseResponse<>(REQUEST_ERROR);

        if(!periodService.getUserExist(periodDto.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.NO_USER);
        }

        periodService.insertPeriod(periodDto);

        return new BaseResponse<>(periodDto);
    }


     /*
     3. 월경 정보 수정하기
     */
     @ResponseBody
     @PatchMapping("/edit/{periodId}")
     public BaseResponse<String> modifyPeriod(@PathVariable("periodId") int periodId, @RequestBody PeriodDto periodDto) {

         if (periodDto.getEmail() == null || periodDto.getStart_date() == null || periodDto.getEnd_date() == null)
             return new BaseResponse<>(REQUEST_ERROR);

         if(!periodService.getUserExist(periodDto.getEmail())){
             return new BaseResponse<>(BaseResponseStatus.NO_USER);
         }

         if(!periodService.getPeriodExist(periodId)){
             return new BaseResponse<>(BaseResponseStatus.NO_PERIOD);
         }

         periodService.modifyPeriod(periodId, periodDto);

         return new BaseResponse<>("월경 정보 수정에 성공하였습니다.");
     }

     /*
     4. 캘린더 해당 월의 월경 정보 가져오기
     */
    @ResponseBody
    @GetMapping("/calendar/{userEmail}/{month}")
    public BaseResponse<List<PeriodDto>> getCalendarPeriod(@PathVariable("userEmail") String userEmail, @PathVariable("month") int month) {

        if(!periodService.getUserExist(userEmail)){
            return new BaseResponse<>(BaseResponseStatus.NO_USER);
        }

        if(!periodService.getPeriodCalendarExist(userEmail, month)){
            return new BaseResponse<>(BaseResponseStatus.NO_MONTH_PERIOD);
        }

        List<PeriodDto> periodDto = periodService.getCalendarPeriod(userEmail, month);

        return new BaseResponse<>(periodDto);
    }

    /*
   5. 홈화면의 월경 정보 가져오기
    */
    @ResponseBody
    @GetMapping("/home/{userEmail}")
    public BaseResponse<HomePeriodDto> getHomePeriodInfo(@PathVariable("userEmail") String userEmail) {

        if(!periodService.getUserExist(userEmail)){
            return new BaseResponse<>(BaseResponseStatus.NO_USER);
        }

        int cycle = periodService.getHomeCycleInfo(userEmail);
        int term = periodService.getHomeTermInfo(userEmail);
        String start_date = periodService.getStartDateInfo(userEmail);

        HomePeriodDto response = new HomePeriodDto(cycle, term, start_date);

        return new BaseResponse<>(response);
    }
}
