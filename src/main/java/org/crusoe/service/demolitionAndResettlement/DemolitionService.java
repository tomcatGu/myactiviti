package org.crusoe.service.demolitionAndResettlement;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.crusoe.entity.User;
import org.crusoe.entity.demolitionAndResettlement.Demolition;
import org.crusoe.repository.jpa.demolitionAndResettlement.DemolitionDao;
import org.crusoe.util.persisterce.DynamicSpecifications;
import org.crusoe.util.persisterce.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class DemolitionService {
	private DemolitionDao demolitionDao;

	public DemolitionDao getDemolitionDao() {
		return demolitionDao;
	}

	@Autowired
	public void setDemolitionDao(DemolitionDao demolitionDao) {
		this.demolitionDao = demolitionDao;
	}

	@Transactional(readOnly = false)
	public void save(Demolition demolition) throws Exception {

		demolitionDao.save(demolition);

	}

	public Page<Demolition> search(HashMap<String, Object> searchParams,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		Map<String, SearchFilter> filters = SearchFilter.parse3(searchParams);
		Specification<Demolition> spec = DynamicSpecifications.bySearchFilter2(
				filters.values(), Demolition.class);
		if (pageRequest.getPageSize() == -1) {
			Page<Demolition> demolitions = new PageImpl<Demolition>(
					demolitionDao.findAll(spec));

			return demolitions;
		} else
			return demolitionDao.findAll(spec, pageRequest);
	}

	public Demolition findById(Long id) {
		// TODO Auto-generated method stub
		return demolitionDao.findOne(id);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub

		demolitionDao.delete(demolitionDao.findOne(id));
	}
}
