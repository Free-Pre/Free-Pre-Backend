package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.UserDao;
import kr.co.FreeAndPre.Dto.UserDto;
import kr.co.FreeAndPre.response.BaseException;
import org.springframework.stereotype.Service;

import static kr.co.FreeAndPre.response.BaseResponseStatus.DATABASE_ERROR;

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

    /*
    Validation
     */

}
