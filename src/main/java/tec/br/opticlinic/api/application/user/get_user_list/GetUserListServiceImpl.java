package tec.br.opticlinic.api.application.user.get_user_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tec.br.opticlinic.api.infra.repository.UserRepository;
import tec.br.opticlinic.api.web.dto.response.UserListResponse;
import tec.br.opticlinic.api.web.dto.response.UserShortResponse;

import java.util.List;

@Service
public class GetUserListServiceImpl implements GetUserListService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserListResponse execute(Integer page, Integer limit) {
        var pageable = PageRequest.of(page - 1, limit);
        var usersPage = userRepository.findAll(pageable);

        List<UserShortResponse> userShortList = usersPage.getContent().stream()
                .map(user -> new UserShortResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEnabled()
                ))
                .toList();

        return new UserListResponse(
                userShortList,
                page,
                limit,
                usersPage.getTotalElements()
        );
    }
}
