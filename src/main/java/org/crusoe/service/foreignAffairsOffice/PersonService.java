package org.crusoe.service.foreignAffairsOffice;

import org.crusoe.entity.foreignAffairsOffice.Person;
import org.crusoe.repository.jpa.foreignAffairsOffice.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("personService")
@Transactional
public class PersonService {
	@Autowired
	private PersonDao personDao;

	public void save(Person person) {
		// TODO Auto-generated method stub
		personDao.save(person);
	}

}
