package org.crusoe.repository.jpa.workflow.governmentInformationDisclosure;

import java.util.List;

import org.crusoe.entity.workflow.governmentInformationDisclosure.StatisticalSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StatisticalSheetDao extends
		PagingAndSortingRepository<StatisticalSheet, Long>,
		JpaSpecificationExecutor<StatisticalSheet> {
	List<StatisticalSheet> findByAnnual(String annual);

	List<StatisticalSheet> findByAnnualAndStatus(String annual, String status);

	Page<StatisticalSheet> findByAnnualAndStatus(String annual, String status,
			Pageable pageable);

	List<StatisticalSheet> findByAnnualAndLoginName(String annual,
			String createUserName);
}
