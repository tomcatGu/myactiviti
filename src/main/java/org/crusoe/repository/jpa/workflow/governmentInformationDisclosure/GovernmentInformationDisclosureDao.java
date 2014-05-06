package org.crusoe.repository.jpa.workflow.governmentInformationDisclosure;

import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GovernmentInformationDisclosureDao extends
		PagingAndSortingRepository<GovernmentInformationDisclosure, Long>,
		JpaSpecificationExecutor<GovernmentInformationDisclosure> {

}
