package org.crusoe.repository.jpa.cms;

import java.util.List;

import org.crusoe.entity.cms.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleDao extends PagingAndSortingRepository<Article, Long>, JpaSpecificationExecutor<Article> {

	@Query("select a from Article a JOIN a.channels c where c.id=?1")
	Page<Article> findByChannelId(Long channelId, Pageable pageable);


}
