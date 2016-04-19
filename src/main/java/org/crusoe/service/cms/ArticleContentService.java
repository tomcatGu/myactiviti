package org.crusoe.service.cms;

import org.crusoe.entity.cms.ArticleContent;
import org.crusoe.repository.jpa.cms.ArticleContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleContentService {
	@Autowired
	ArticleContentDao articleContentDao;

	public ArticleContent saveArticleContent(ArticleContent aContent) {
		// TODO Auto-generated method stub
		return articleContentDao.save(aContent);
	}
}
