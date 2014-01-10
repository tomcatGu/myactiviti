package org.crusoe.service.demolitionAndResettlement;

import org.crusoe.entity.demolitionAndResettlement.PersonOfFillIn;
import org.crusoe.repository.jpa.demolitionAndResettlement.PersonOfFillInDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PersonOfFillInService {
	@Autowired
	private PersonOfFillInDao personOfFillInDao;

	public PersonOfFillIn find(String userName) {

		return personOfFillInDao.findByName(userName);
	}

	public void save(PersonOfFillIn pofi) {
		// TODO Auto-generated method stub
		personOfFillInDao.save(pofi);
	}

}
