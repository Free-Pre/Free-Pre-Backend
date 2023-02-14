package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/period")
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
    2. 월경 정보 입력하기
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> insertPeriod(@RequestBody PeriodDto periodDto) {
        int periodId = periodService.insertPeriod(periodDto);
        if(periodId == 1)
            return new BaseResponse<>("월경 정보 입력에 성공하였습니다.");
        else
            return new BaseResponse<>("월경 정보 입력에 실패하였습니다.");
    }


     /*
    3. 월경 정보 수정하기
     */
}
