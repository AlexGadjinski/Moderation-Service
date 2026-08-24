package app.repository;

import app.model.Report;
import app.model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findByCommunityId(UUID communityId);

    List<Report> findByCommunityIdAndStatus(UUID communityId, ReportStatus status);
}
