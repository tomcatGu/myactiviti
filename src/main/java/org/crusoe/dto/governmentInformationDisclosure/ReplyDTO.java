package org.crusoe.dto.governmentInformationDisclosure;

import java.util.Date;
import java.util.List;

import org.crusoe.dto.AttachmentDTO;

import com.google.common.collect.Lists;

public class ReplyDTO {
	private Long id;
	private String reply;
	private String userId;
	private String username;
	private Date replyTime;
	private List<AttachmentDTO> attachments = Lists.newArrayList();

	public List<AttachmentDTO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AttachmentDTO> attachments) {
		this.attachments = attachments;
	}

}
