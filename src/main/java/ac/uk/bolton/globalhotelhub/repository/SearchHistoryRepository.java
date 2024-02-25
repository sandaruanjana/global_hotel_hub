package ac.uk.bolton.globalhotelhub.repository;

import ac.uk.bolton.globalhotelhub.dto.SearchHistoryDTO;
import ac.uk.bolton.globalhotelhub.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    @Query(value = "SELECT s.id, s.location, s.check_in, s.checkout, s.rooms, s.adults, s.child, s.source, s.created_at, s.updated_at FROM search_history s WHERE s.user_id = :userId", nativeQuery = true)
    List<Object[]> getAllByUserId(@Param("userId") Long userId);

}
