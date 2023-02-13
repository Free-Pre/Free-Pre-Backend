package kr.co.FreeAndPre.Controller;

import kr.co.FreeAndPre.Provider.PeriodProvider;
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
    private PeriodProvider periodProvider;

    public PeriodController(PeriodService periodService, PeriodProvider periodProvider) {
        this.periodService = periodService;
        this.periodProvider = periodProvider;
    }

    /*
    1. 월경 정보 가져오기
     */
    @ResponseBody
    @GetMapping("/{periodId}")
    public BaseResponse<GetPeriodRes> getPeriodInfoById (@PathVariable("periodId") int periodId) {
        try{
            GetPeriodRes getPeriodRes = periodProvider.getPeriodById(periodId);
            return new BaseResponse<>(getPeriodRes);
        } catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    2. 월경 정보 입력하기
     */


     /*
    3. 월경 정보 수정하기
     */
}
