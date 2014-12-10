package org.crusoe.repository.jpa.workflow.governmentInformationDisclosure;

import org.crusoe.entity.workflow.governmentInformationDisclosure.Datum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DatumDao extends PagingAndSortingRepository<Datum, Long>,
		JpaSpecificationExecutor<Datum> {

}
