package org.crusoe.entity.cms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;

@Entity
@Table(name = "cms_article")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article {
	private Long id;
	private String title;
	private String author;
	private long sequenceIndex;
	private String state;
	private long clicks;
	private DateTime created;
	private String createUser;
	private ArticleContent aticleContent;
	private List<Channel> channels = new ArrayList<Channel>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public ArticleContent getArticleContent() {
		return aticleContent;
	}

	public void setArticleContent(ArticleContent articleContent) {
		this.aticleContent = articleContent;
	}

	// 多对多定义
	@ManyToMany
	@JoinTable(name = "cms_article_channel", joinColumns = { @JoinColumn(name = "article_id") }, inverseJoinColumns = {
			@JoinColumn(name = "channel_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
