package in.dminc.repositories;

import in.dminc.domain.AuthorUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, Long> {

}
