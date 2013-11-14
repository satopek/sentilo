/*
 * Sentilo
 *   
 * Copyright (C) 2013 Institut Municipal d’Informàtica, Ajuntament de  Barcelona.
 *   
 * This program is licensed and may be used, modified and redistributed under the
 * terms  of the European Public License (EUPL), either version 1.1 or (at your 
 * option) any later version as soon as they are approved by the European 
 * Commission.
 *   
 * Alternatively, you may redistribute and/or modify this program under the terms
 * of the GNU Lesser General Public License as published by the Free Software 
 * Foundation; either  version 3 of the License, or (at your option) any later 
 * version. 
 *   
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. 
 *   
 * See the licenses for the specific language governing permissions, limitations 
 * and more details.
 *   
 * You should have received a copy of the EUPL1.1 and the LGPLv3 licenses along 
 * with this program; if not, you may find them at: 
 *   
 *   https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *   http://www.gnu.org/licenses/ 
 *   and 
 *   https://www.gnu.org/licenses/lgpl.txt
 */
package org.sentilo.web.catalog.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.sentilo.web.catalog.utils.Constants;
import org.sentilo.web.catalog.utils.TagUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;


@Document
public class Component implements CatalogDocument {

	private static final long serialVersionUID = 1L;	

	@Id	
	private String id;

	@NotBlank
	@Pattern(regexp = Constants.VALIDATION_ENTITY_NAME_REGEXP)
	private String name;

	private String description;
	
	@NotBlank
	private String providerId;

	private Location location;	

	@DateTimeFormat(pattern = Constants.DATE_FORMAT)
	private Date createdAt;

	@DateTimeFormat(pattern = Constants.DATE_FORMAT)
	private Date updateAt;

	private int mobile = Constants.MOBILE;

	private String parentId;

	private String tags;

	private Boolean publicAccess = Boolean.FALSE;
	
	private String componentType;

	public Component() {
		
	}

	public Component(String id) {
		this();
		this.id = id;		
	}
	
	public static String buildId(String providerId, String name){		
		return (StringUtils.hasText(name) && StringUtils.hasText(providerId))?providerId + "." + name:null;					
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Component) || id == null) {
			return false;
		}
		Component other = (Component) obj;		
		return getId().equals(other.getId());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result*super.hashCode();
	}

	@Override
	public String getId() {
		if (!StringUtils.hasText(this.id) && StringUtils.hasText(name) && StringUtils.hasText(providerId)) {
			this.id = buildId(providerId, name);
		}

		return this.id;			
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getMobile() {
		return mobile;
	}

	@JsonIgnore
	public boolean isMobileComponent() {
		return mobile == Constants.MOBILE;
	}

	@JsonIgnore
	public boolean isStaticComponent() {
		return mobile == Constants.STATIC;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@JsonIgnore
	public boolean isRoot() {
		return !StringUtils.hasText(parentId);
	}	

	public String getTags() {
		return tags;
	}

	public List<String> getTagsAsList() {
		return TagUtils.toStringList(tags);
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Boolean getPublicAccess() {
		return publicAccess;
	}

	public void setPublicAccess(Boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
}