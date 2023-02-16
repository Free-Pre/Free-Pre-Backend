package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.PeriodDao;
import kr.co.FreeAndPre.Dao.UserDao;
import kr.co.FreeAndPre.Dto.PeriodDto;
import kr.co.FreeAndPre.Dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public Boolean getUserExist(String userEmail) {
        Boolean exist = UserDao.getInstance().getUserExist(userEmail);
        return exist;
    }

    public int insertUser(UserDto userDto) {
        int userSuccess = UserDao.getInstance().insertUser(userDto);
        return userSuccess;
    }
}
