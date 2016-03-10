package org.crusoe.dto.cms;

import java.util.ArrayList;
import java.util.List;

import org.crusoe.entity.cms.ArticleContent;
import org.crusoe.entity.cms.Channel;
import org.joda.time.DateTime;

public class ArticleContentDTO {
	private long id;
	private String title;
	private String author;
	private long sequenceIndex;
	private String state;
	private long clicks;
	private DateTime created;
	private String createUser;
	private ArticleContent aticleContent;
	private List<Channel> channels = new ArrayList<Channel>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getSequenceIndex() {
		return sequenceIndex;
	}
	public void setSequenceIndex(long sequenceIndex) {
		this.sequenceIndex = sequenceIndex;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getClicks() {
		return clicks;
	}
	public void setClicks(long clicks) {
		this.clicks = clicks;
	}
	public DateTime getCreated() {
		return created;
	}
	public void setCreated(DateTime created) {
		this.created = created;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public ArticleContent getAticleContent() {
		return aticleContent;
	}
	public void setAticleContent(ArticleContent aticleContent) {
		this.aticleContent = aticleContent;
	}
	public List<Channel> getChannels() {
		return channels;
	}
	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
}
