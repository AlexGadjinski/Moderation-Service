package app.repository;

import app.model.Report;
import app.model.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    Page<Report> findByCommunityId(UUID communityId, Pageable pageable);

    Page<Report> findByCommunityIdAndStatus(UUID communityId, ReportStatus status, Pageable pageable);
}
