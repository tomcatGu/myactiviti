package org.crusoe.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

public class RoleDTO implements Serializable {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id.toString();
	}

	public RoleDTO() {
	}

	public RoleDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {

		// TODO Auto-generated method stub
		if (this.id != null)
			return this.id.intValue();
		else
			return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof RoleDTO) {
			RoleDTO r = (RoleDTO) obj;
			System.out.println(r.id + "<===>" + this.id);
			if (r.getId().equals(this.id)) {

				return true;
			} else
				return false;
		} else
			return false;
		// return super.equals(obj);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Long id;
	private String name = "";
	private List<PermissionDTO> permissions = new ArrayList<PermissionDTO>();

}
