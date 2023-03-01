package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/freepre/period")
public class PeriodController {

    private PeriodService periodService;

    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    /*
    1. 월경 정보 가져오기
     */
    @ResponseBody
    @GetMapping("/{periodId}")
    public BaseResponse<PeriodDto> getPeriodInfoById (@PathVariable("periodId") int periodId) {
        PeriodDto periodDto = periodService.getPeriodById(periodId);
        return new BaseResponse<>(periodDto);
    }

    /*
    2. 회원가입 직후 첫 월경일 정보 입력
     */
    @ResponseBody
    @PostMapping("/first")
    public BaseResponse<String> insertFirstPeriod(@RequestBody PeriodDto periodDto) {
        int periodSuccess = periodService.insertFirstPeriod(periodDto);
        if(periodSuccess == 0)
            return new BaseResponse<>("첫 월경 정보 입력에 실패하였습니다.");
        else
            return new BaseResponse<>("첫 월경 정보 입력에 성공하였습니다.");
    }

    /*
    2-1. 월경 정보 입력하기
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> insertPeriod(@RequestBody PeriodDto periodDto) {
        int periodSuccess = periodService.insertPeriod(periodDto);
        if(periodSuccess == 0)
            return new BaseResponse<>("월경 정보 입력에 실패하였습니다.");
        else
            return new BaseResponse<>("월경 정보 입력에 성공하였습니다.");
    }


     /*
    3. 월경 정보 수정하기
     */
}
