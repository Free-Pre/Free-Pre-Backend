package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.PeriodDao;
import kr.co.FreeAndPre.Dto.PeriodDto;
import org.springframework.stereotype.Service;

@Service
public class PeriodService {
    public PeriodDto getPeriodById (int periodId) {
        PeriodDto periodDto = PeriodDao.getInstance().getPeriodById(periodId);
        return periodDto;
    }

    public int insertFirstPeriod(PeriodDto periodDto) {
        int periodId = PeriodDao.getInstance().insertFirstPeriod(periodDto);
        return periodId;
    }

    public int insertPeriod(PeriodDto periodDto) {
        int periodId = PeriodDao.getInstance().insertPeriod(periodDto);
        return periodId;
    }
}
