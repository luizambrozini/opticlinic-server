package tec.br.opticlinic.api.application.people.get_people_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tec.br.opticlinic.api.infra.repository.PeopleRepository;
import tec.br.opticlinic.api.web.dto.response.PeopleListResponse;
import tec.br.opticlinic.api.web.dto.response.PeopleShortResponse;
import tec.br.opticlinic.api.web.dto.response.UserListResponse;

import java.util.List;

@Service
public class GetPeopleListServiceImpl implements GetPeopleListService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public PeopleListResponse execute(Integer page, Integer limit) {
        var pageable = PageRequest.of(page - 1, limit);
        var peoplesPage = peopleRepository.findAll(pageable);

        List<PeopleShortResponse> peopleShortList = peoplesPage.getContent().stream()
                .map(user -> new PeopleShortResponse(
                        user.getId(),
                        user.getName(),
                        user.getCpf()
                ))
                .toList();

        return new PeopleListResponse(
                peopleShortList,
                page,
                limit,
                peoplesPage.getTotalElements()
        );
    }
}
