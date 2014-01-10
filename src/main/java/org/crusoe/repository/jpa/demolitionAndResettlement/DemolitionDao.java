package org.crusoe.repository.jpa.demolitionAndResettlement;

import org.crusoe.entity.demolitionAndResettlement.Demolition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DemolitionDao extends
		PagingAndSortingRepository<Demolition, Long>,
		JpaSpecificationExecutor<Demolition> {

}
