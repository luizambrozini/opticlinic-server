package tec.br.opticlinic.api.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PeopleListResponse {
    private List<PeopleShortResponse> peoples;
    private Integer page;
    private Integer limit;
    private Long total;
}
