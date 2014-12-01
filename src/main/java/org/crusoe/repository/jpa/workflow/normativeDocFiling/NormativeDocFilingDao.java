package org.crusoe.repository.jpa.workflow.normativeDocFiling;

import java.util.Date;
import java.util.List;

import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NormativeDocFilingDao extends
		PagingAndSortingRepository<NormativeDocFiling, Long>,
		JpaSpecificationExecutor<NormativeDocFiling> {

	List<NormativeDocFiling> findByCreateOnBetween(Date startTime, Date endTime);

	@Query("select o from Organization o where o.parent.id=?1 order by o.sequence")
	List<NormativeDocFiling> findByTitleAndCreateTimeAndOrganization(
			String title, Date startTime, Date endTime, String organizationName);
}
