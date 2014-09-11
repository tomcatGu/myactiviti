package org.crusoe.repository.jpa.workflow.governmentInformationDisclosure;

import org.crusoe.entity.workflow.governmentInformationDisclosure.StatisticalSheet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StatisticalSheetDao extends
		PagingAndSortingRepository<StatisticalSheet, Long>,
		JpaSpecificationExecutor<StatisticalSheet> {

}
