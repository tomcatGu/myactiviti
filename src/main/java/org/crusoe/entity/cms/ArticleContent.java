package org.crusoe.entity.cms;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "cms_articleContent")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArticleContent {
private Long id;
private String articleContent;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
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

@OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy = "articleContent")
public Article getArticle() {
	return article;
}
public void setArticle(Article article) {
	this.article = article;
}
private Article article;
}
