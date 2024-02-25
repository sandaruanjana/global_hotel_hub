package ac.uk.bolton.globalhotelhub.repository;


import ac.uk.bolton.globalhotelhub.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Hotel findByTitleAndSource(String title, String source);

    List<Hotel> findBySource(String source);

    List<Hotel> findByAddress(String address);

    Optional<Hotel> findById(Long id);

}
