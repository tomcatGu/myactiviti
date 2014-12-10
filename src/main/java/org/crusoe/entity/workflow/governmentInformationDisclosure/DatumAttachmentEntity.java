package org.crusoe.entity.workflow.governmentInformationDisclosure;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DatumAttachmentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3075198966748528330L;
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
