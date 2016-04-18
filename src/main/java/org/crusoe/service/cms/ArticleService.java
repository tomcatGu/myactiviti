package org.crusoe.service.cms;

import java.util.List;

import org.crusoe.entity.cms.Article;
import org.crusoe.repository.jpa.cms.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	@Autowired
	ArticleDao articleDao;

	public List<Article> findByChannelId(Long channelId, Pageable pageable) {
		// TODO Auto-generated method stub
		return articleDao.findByChannelId(channelId, pageable);
	}

}
