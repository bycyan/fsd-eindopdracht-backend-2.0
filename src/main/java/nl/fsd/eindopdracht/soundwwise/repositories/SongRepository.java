package nl.fsd.eindopdracht.soundwwise.repositories;

import nl.fsd.eindopdracht.soundwwise.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
