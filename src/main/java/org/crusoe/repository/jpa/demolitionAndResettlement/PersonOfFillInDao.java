package org.crusoe.repository.jpa.demolitionAndResettlement;

import org.crusoe.entity.demolitionAndResettlement.PersonOfFillIn;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonOfFillInDao extends
		PagingAndSortingRepository<PersonOfFillIn, Long>,
		JpaSpecificationExecutor<PersonOfFillIn> {
	PersonOfFillIn findByName(String name);
}
