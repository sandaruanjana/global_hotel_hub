package ac.uk.bolton.globalhotelhub.service;

import ac.uk.bolton.globalhotelhub.util.AjaxResponse;

import java.util.List;

public interface SearchHistoryService {
    AjaxResponse<Object> getUserSearchHistory();
}
