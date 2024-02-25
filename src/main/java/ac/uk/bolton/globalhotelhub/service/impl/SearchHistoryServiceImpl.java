package ac.uk.bolton.globalhotelhub.service.impl;

import ac.uk.bolton.globalhotelhub.dto.SearchHistoryDTO;
import ac.uk.bolton.globalhotelhub.dto.UserDTO;
import ac.uk.bolton.globalhotelhub.entity.SearchHistory;
import ac.uk.bolton.globalhotelhub.repository.SearchHistoryRepository;
import ac.uk.bolton.globalhotelhub.service.SearchHistoryService;
import ac.uk.bolton.globalhotelhub.service.UserService;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    private final UserService userService;

    @Override
    public AjaxResponse<Object> getUserSearchHistory() {

        try {
            UserDTO currentUser = userService.getCurrentUser();

            List<Object[]> searchHistoryList = searchHistoryRepository.getAllByUserId(currentUser.getId());

            List<SearchHistoryDTO> searchHistoryDTOList = new java.util.ArrayList<>(Collections.emptyList());

            for (Object[] row : searchHistoryList) {
                searchHistoryDTOList.add(new SearchHistoryDTO(
                        (Long) row[0], // id
                        (String) row[1], // location
                        (String) row[2], // check_in
                        (String) row[3], // checkout
                        (String) row[4], // rooms
                        (String) row[5], // adults
                        (String) row[6], // child
                        (String) row[7], // source
                        (Timestamp) row[8], // created_at
                        (Timestamp) row[9] // updated_at
                ));
            }

            return AjaxResponse.success(searchHistoryDTOList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxResponse.error();

    }
}
