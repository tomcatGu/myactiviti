package org.crusoe.repository.jpa.workflow.normativeDocFiling;

import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NormativeDocFilingDao extends
		PagingAndSortingRepository<NormativeDocFiling, Long>,
		JpaSpecificationExecutor<NormativeDocFiling> {
}
