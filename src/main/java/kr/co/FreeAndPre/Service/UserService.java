package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.PeriodDao;
import kr.co.FreeAndPre.Dao.UserDao;
import kr.co.FreeAndPre.Dto.PeriodDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public Boolean getUserExist(String userEmail) {
        Boolean exist = UserDao.getInstance().getUserExist(userEmail);
        return exist;
    }
}
