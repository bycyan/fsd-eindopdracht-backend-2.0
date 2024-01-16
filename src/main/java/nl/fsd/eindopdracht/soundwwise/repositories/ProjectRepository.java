package nl.fsd.eindopdracht.soundwwise.repositories;

import nl.fsd.eindopdracht.soundwwise.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
