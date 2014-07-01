package org.crusoe.repository.jpa.workflow.governmentInformationDisclosure;

import org.crusoe.entity.workflow.governmentInformationDisclosure.Application;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplicationDao extends
		PagingAndSortingRepository<Application, Long>,
		JpaSpecificationExecutor<Application> {

}
