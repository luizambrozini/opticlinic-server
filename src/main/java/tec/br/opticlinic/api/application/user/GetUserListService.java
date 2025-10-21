package tec.br.opticlinic.api.application.user;

import tec.br.opticlinic.api.web.dto.response.UserListResponse;

public interface GetUserListService {
    UserListResponse execute(Integer page, Integer limit);
}
