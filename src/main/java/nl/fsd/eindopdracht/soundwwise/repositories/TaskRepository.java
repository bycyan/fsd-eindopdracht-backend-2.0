package nl.fsd.eindopdracht.soundwwise.repositories;

import nl.fsd.eindopdracht.soundwwise.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository <Task, Long> {
}
