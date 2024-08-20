package nl.hu.inno.humc.monoliet.cursusverloop.repository.announcement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.hu.inno.humc.monoliet.cursusverloop.domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByCursus(Long cursus);
}
