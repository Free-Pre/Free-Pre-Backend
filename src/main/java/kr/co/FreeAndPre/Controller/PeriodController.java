package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Model.PostPeriodRes;
import kr.co.FreeAndPre.Service.PeriodService;
import kr.co.FreeAndPre.response.BaseException;
import kr.co.FreeAndPre.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import kr.co.FreeAndPre.Model.GetPeriodRes;

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



     /*
    3. 월경 정보 수정하기
     */
}
