package org.crusoe.dto;

import org.apache.lucene.document.Field;

public class FieldDTO {
	private String id;
	private String fieldName;
	private String fieldContent;
	private Field.Store store;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldContent() {
		return fieldContent;
	}

	public void setFieldContent(String fieldContent) {
		this.fieldContent = fieldContent;
	}

	public Field.Store getStore() {
		return store;
	}

	public void setStore(Field.Store store) {
		this.store = store;
	}

}
