package nl.fsd.eindopdracht.soundwwise.repositories;

import nl.fsd.eindopdracht.soundwwise.models.Authority;
import nl.fsd.eindopdracht.soundwwise.models.AuthorityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityKey> {
        void deleteByProjectId(Long projectId);
}

