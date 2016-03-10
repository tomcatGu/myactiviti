package org.crusoe.dto.cms;

import java.util.ArrayList;
import java.util.List;

import org.crusoe.entity.cms.Article;
import org.crusoe.entity.cms.Channel;

public class ChannelDTO {
	private Long id;
	private Channel parent;
	private String title;
	private int sequenceIndex;
	private String state;
	private List<Article> articles = new ArrayList<Article>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Channel getParent() {
		return parent;
	}
	public void setParent(Channel parent) {
		this.parent = parent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSequenceIndex() {
		return sequenceIndex;
	}
	public void setSequenceIndex(int sequenceIndex) {
		this.sequenceIndex = sequenceIndex;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
