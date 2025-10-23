package tec.br.opticlinic.api.application.people.get_people_list;

import tec.br.opticlinic.api.web.dto.response.PeopleListResponse;

public interface GetPeopleListService {
    PeopleListResponse execute(Integer page, Integer limit);
}
