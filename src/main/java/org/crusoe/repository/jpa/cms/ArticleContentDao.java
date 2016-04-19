package org.crusoe.repository.jpa.cms;

import org.crusoe.entity.cms.ArticleContent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleContentDao
		extends PagingAndSortingRepository<ArticleContent, Long>, JpaSpecificationExecutor<ArticleContent> {

}
