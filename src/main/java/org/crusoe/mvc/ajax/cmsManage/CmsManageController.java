package org.crusoe.mvc.ajax.cmsManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.crusoe.dto.cms.ArticleDTO;
import org.crusoe.dto.cms.ChannelDTO;
import org.crusoe.entity.cms.Article;
import org.crusoe.entity.cms.ArticleContent;
import org.crusoe.entity.cms.Channel;
import org.crusoe.service.cms.ArticleContentService;
import org.crusoe.service.cms.ArticleService;
import org.crusoe.service.cms.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cmsManage")
public class CmsManageController {

	@Autowired
	ChannelService channelService;
	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleContentService articleContentService;

	@RequestMapping(value = "index")
	public String index(ServletRequest request) {

		return "cmsManage/index";
	}

	@RequestMapping(value = "channel/index")
	public String channelIndex(ServletRequest request) {

		return "cmsManage/channel/index";
	}
	@RequestMapping(value = "article/index")
	public String articleIndex(ServletRequest request) {

		return "cmsManage/article/index";
	}
	@RequestMapping(value = "channel/data", method = RequestMethod.GET)
	public @ResponseBody ChannelDTO channelById(@RequestParam(value = "id") Long id) {

		Channel c = channelService.findById(id);

		ChannelDTO cDTO = new ChannelDTO();
		cDTO.setId(c.getId());
		cDTO.setName(c.getTitle());
		cDTO.setState(c.getState());
		cDTO.setSequenceIndex(c.getSequenceIndex());
		Iterator iter = c.getChildren().iterator();
		while (iter.hasNext()) {
			ChannelDTO ccDTO = new ChannelDTO();
			Channel cc = (Channel) iter.next();
			ccDTO.setId(cc.getId());
			ccDTO.setName(cc.getTitle());
			ccDTO.setState(cc.getState());
			ccDTO.setSequenceIndex(cc.getSequenceIndex());
			// ccDTO.setParent(cc.getParent());
			cDTO.getChildren().add(ccDTO);
		}

		return cDTO;

	}

	@RequestMapping(value = "channel/data", method = { RequestMethod.POST })
	public @ResponseBody ChannelDTO add(@RequestBody ChannelDTO c) {
		Channel channel = new Channel();
		channel.setTitle(c.getName());
		channel.setState(c.getState());
		channel.setSequenceIndex(c.getSequenceIndex());
		Channel parent = (Channel) channelService.findById(c.getParent().getId());
		channel.setParent(parent);
		Channel savedChannel = channelService.save(channel);

		c.setId(savedChannel.getId());
		return c;
	}

	@RequestMapping(value = "channel/data/{id}", method = { RequestMethod.PUT })
	public @ResponseBody ChannelDTO update(@PathVariable(value = "id") Long id, @RequestBody ChannelDTO c) {
		Channel channel = channelService.findById(id);
		// channel.setId(c.getId());
		channel.setTitle(c.getName());
		channel.setSequenceIndex(c.getSequenceIndex());
		channel.setState(c.getState());

		channel.setParent(channelService.findById(c.getParent().getId()));

		Channel savedChannel = channelService.save(channel);

		return c;
	}

	@RequestMapping(value = "channel/data/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody Map<String, ? extends Object> delete(@PathVariable(value = "id") Long id) {
		channelService.deleteById(id);

		Map<String, String> msgs = new HashMap<String, String>();

		msgs.put("msg", "删除成功");
		return msgs;

	}

	@RequestMapping(value = "article/data", method = RequestMethod.GET)
	public @ResponseBody List<ArticleDTO> articleByChannnelId(@RequestParam(value = "channelId") Long channelId,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "count", defaultValue = "10") int count,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "sortDesc", defaultValue = "true") boolean sortDesc) {
		Sort sortRequest = sortDesc ? new Sort(Direction.DESC, new String[] { sort })
				: new Sort(Direction.ASC, new String[] { sort });

		PageRequest pageRequest = new PageRequest(start / count, count, sortRequest);
		List<ArticleDTO> articleDTOs = new ArrayList<ArticleDTO>();

		List<Article> articles = articleService.findByChannelId(channelId, pageRequest);

		Iterator iter = articles.iterator();
		while (iter.hasNext()) {
			Article a = (Article) iter.next();
			ArticleDTO aDTO = new ArticleDTO();
			aDTO.setId(a.getId());
			aDTO.setAuthor(a.getAuthor());
			aDTO.setClicks(a.getClicks());
			aDTO.setCreated(a.getCreated());
			aDTO.setSequenceIndex(a.getSequenceIndex());
			aDTO.setState(a.getState());
			aDTO.setTitle(a.getTitle());

			articleDTOs.add(aDTO);

		}

		return articleDTOs;

	}

	@RequestMapping(value = "article/data", method = RequestMethod.POST)
	public @ResponseBody ArticleDTO addArticle(@RequestBody ArticleDTO aDTO) {
		Article a = new Article();
		ArticleContent aContent = new ArticleContent();

		aContent.setArticleContent(aDTO.getArticleContentDTO().getArticleContent());
		aContent = articleContentService.saveArticleContent(aContent);

		a.setAuthor(aDTO.getAuthor());
		a.setClicks(aDTO.getClicks());
		a.setCreated(aDTO.getCreated());
		a.setCreateUser(aDTO.getCreateUser());
		a.setSequenceIndex(aDTO.getSequenceIndex());
		a.setState(aDTO.getState());
		a.setTitle(aDTO.getTitle());
		a.setArticleContent(aContent);
		Iterator iter = aDTO.getChannelDTOs().iterator();
		while (iter.hasNext()) {
			ChannelDTO channelDTO = (ChannelDTO) iter.next();

			a.getChannels().add(channelService.findById(channelDTO.getId()));

		}

		a = articleService.saveArticle(a);

		aDTO.setId(a.getId());

		return aDTO;

	}

}
