package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.PeriodDao;
import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Model.GetPeriodRes;
import org.springframework.stereotype.Service;

@Service
public class PeriodService {
    public PeriodDto getPeriodById (int periodId) {
        PeriodDto periodDto = PeriodDao.getInstance().getAbuseByIdx(periodId);
        return periodDto;
    }
}
