package org.crusoe.repository.jpa.foreignAffairsOffice;

import org.crusoe.entity.Resource;
import org.crusoe.entity.foreignAffairsOffice.Enterprise;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EnterpriseDao extends PagingAndSortingRepository<Enterprise, Long>, JpaSpecificationExecutor<Resource>{

}
