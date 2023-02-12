package kr.co.FreeAndPre.Provider;

import kr.co.FreeAndPre.Dao.PeriodDao;
import kr.co.FreeAndPre.Model.GetPeriodRes;
import kr.co.FreeAndPre.response.BaseException;
import org.springframework.stereotype.Service;

import static kr.co.FreeAndPre.response.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PeriodProvider {

    private final PeriodDao periodDao;

    public PeriodProvider(PeriodDao periodDao) {
        this.periodDao = periodDao;
    }

    public GetPeriodRes getPeriodById (int periodId) throws BaseException {
        //존재하는 회원에 대한 조회인지 확인 필요
        try {
            GetPeriodRes getAbuseRes = periodDao.getAbuseByIdx(periodId);
            return getAbuseRes;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
