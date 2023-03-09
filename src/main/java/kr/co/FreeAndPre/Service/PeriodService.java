package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.PeriodDao;
import kr.co.FreeAndPre.Dao.UserDao;
import kr.co.FreeAndPre.Dto.PeriodDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {
    public List<PeriodDto> getPeriodInfoByEmail (String userEmail) {
        return PeriodDao.getInstance().getPeriodInfoByEmail(userEmail);
    }

    public void insertFirstPeriod(PeriodDto periodDto) {
        PeriodDao.getInstance().insertFirstPeriod(periodDto);
    }

    public void insertPeriod(PeriodDto periodDto) {
        PeriodDao.getInstance().insertPeriod(periodDto);
    }

    public void modifyPeriod(int periodId, PeriodDto periodDto) {
        PeriodDao.getInstance().modifyPeriod(periodId, periodDto);
    }

    /*
   Validation
    */
    public Boolean getUserExist(String userEmail) {
        return UserDao.getInstance().getUserExist(userEmail);
    }
}
