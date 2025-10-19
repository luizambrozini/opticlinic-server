package tec.br.opticlinic.api.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tec.br.opticlinic.api.infra.dao.UserDao;
import tec.br.opticlinic.api.web.dto.response.UserProfieResponse;
import tec.br.opticlinic.api.web.error.ErrorCode;
import tec.br.opticlinic.api.web.error.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class GetUserProfileService {

    private final UserDao userDao;

    public UserProfieResponse execute(Long id) {
        var userOptional = userDao.findById(id);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(ErrorCode.USER_NOT_FOUND,"User not found");
        }

        var user = userOptional.get();

        return new UserProfieResponse(user.getUsername(), user.getCreatedAt());
    }

    public UserProfieResponse execute(String username) {
        var userOptional = userDao.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(ErrorCode.USER_NOT_FOUND,"User not found");
        }

        var user = userOptional.get();

        return new UserProfieResponse(user.getUsername(), user.getCreatedAt());
    }
}
