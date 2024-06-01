package in.dminc.repositories;

import in.dminc.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookUuidRepository extends JpaRepository<BookUuid, Long> {
}
