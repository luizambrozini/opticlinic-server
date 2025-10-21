package tec.br.opticlinic.api.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserListResponse {
    private List<UserShortResponse> users;
    private Integer page;
    private Integer limit;
    private Long total;
}
