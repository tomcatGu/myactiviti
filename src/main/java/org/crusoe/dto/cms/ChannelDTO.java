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
	private List<ArticleDTO> articles = new ArrayList<ArticleDTO>();
	private List<ChannelDTO> children=new ArrayList<ChannelDTO>();

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

	public String getName() {
		return title;
	}

	public void setName(String title) {
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

	public List<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDTO> articles) {
		this.articles = articles;
	}

	public List<ChannelDTO> getChildren() {
		return children;
	}

	public void setChildren(List<ChannelDTO> children) {
		this.children = children;
	}
}
