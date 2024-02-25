package ac.uk.bolton.globalhotelhub.controller;

import ac.uk.bolton.globalhotelhub.service.SearchHistoryService;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search/history")
@RequiredArgsConstructor
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    @PostMapping("/")
    public AjaxResponse<Object> getSearchHistory() {
        return searchHistoryService.getUserSearchHistory();
    }

}
