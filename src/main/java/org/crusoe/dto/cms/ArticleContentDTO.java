package org.crusoe.dto.cms;

import java.util.ArrayList;
import java.util.List;

import org.crusoe.entity.cms.ArticleContent;
import org.crusoe.entity.cms.Channel;
import org.joda.time.DateTime;

public class ArticleContentDTO {
	private long id;
	private String articleContent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

}
