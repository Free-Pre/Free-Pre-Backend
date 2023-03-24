package kr.co.FreeAndPre.Service;

import kr.co.FreeAndPre.Dao.UserDao;
import kr.co.FreeAndPre.Dto.UserDto;
import kr.co.FreeAndPre.response.BaseException;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    public Boolean getUserExist(String userEmail) {
        return UserDao.getInstance().getUserExist(userEmail);
    }

    public void insertFreeUser(UserDto userDto) {
        UserDao.getInstance().insertFreeUser(userDto);
    }

    public void insertPreUser(UserDto userDto) {
        UserDao.getInstance().insertPreUser(userDto);
    }

    public void modifyUserNickname(String userEmail, UserDto userDto) {
        UserDao.getInstance().modifyUserNickname(userEmail, userDto);
    }

    public void pretofree(String userEmail, UserDto userDto) {
        UserDao.getInstance().pretofree(userEmail, userDto);
    }

    public void freetopre(String userEmail, UserDto userDto) {
        UserDao.getInstance().freetopre(userEmail, userDto);
    }

    public void deleteUser(String userEmail) {
        UserDao.getInstance().deleteUser(userEmail);
    }

    public Boolean getUserCycle(String userEmail) {
        return UserDao.getInstance().getUserCycle(userEmail);
    }

}
