package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.PeriodDao;
import kr.co.FreeAndPre.Dto.PeriodDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {
    public List<PeriodDto> getPeriodInfoByEmail (String userEmail) {
        List<PeriodDto> periodDto = PeriodDao.getInstance().getPeriodInfoByEmail(userEmail);
        return periodDto;
    }

    public int insertFirstPeriod(PeriodDto periodDto) {
        int result = PeriodDao.getInstance().insertFirstPeriod(periodDto);
        return result;
    }

    public int insertPeriod(PeriodDto periodDto) {
        int result = PeriodDao.getInstance().insertPeriod(periodDto);
        return result;
    }

    public int modifyPeriod(int periodId, PeriodDto periodDto) {
        int result = PeriodDao.getInstance().modifyPeriod(periodId, periodDto);
        return result;
    }
}
