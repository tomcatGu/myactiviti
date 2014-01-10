package org.crusoe.repository.jpa.foreignAffairsOffice;

import org.crusoe.entity.Resource;
import org.crusoe.entity.foreignAffairsOffice.Person;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonDao extends PagingAndSortingRepository<Person, Long>, JpaSpecificationExecutor<Resource> {

}
