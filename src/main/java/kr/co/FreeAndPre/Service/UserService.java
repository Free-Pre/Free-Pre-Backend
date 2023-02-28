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

    public int insertFreeUser(UserDto userDto) {
        int userSuccess = UserDao.getInstance().insertFreeUser(userDto);
        return userSuccess;
    }

    public int insertPreUser(UserDto userDto) {
        int userSuccess = UserDao.getInstance().insertPreUser(userDto);
        return userSuccess;
    }

    public int modifyUserNickname(String userEmail, UserDto userDto) {
        int userSuccess = UserDao.getInstance().modifyUserNickname(userEmail, userDto);
        return userSuccess;
    }

    public int pretofree(String userEmail, UserDto userDto) {
        int userSuccess = UserDao.getInstance().pretofree(userEmail, userDto);
        return userSuccess;
    }

    public int freetopre(String userEmail, UserDto userDto) {
        int userSuccess = UserDao.getInstance().freetopre(userEmail, userDto);
        return userSuccess;
    }

    /*
    Validation
     */

}
