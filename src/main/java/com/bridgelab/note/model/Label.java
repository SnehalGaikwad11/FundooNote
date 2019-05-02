package com.bridgelab.note.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="labels")
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long labelId;
	
	private LocalDateTime modifiedDate;
	private String labelName;
	private LocalDateTime createdDate;
	private long userId;
	
	

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Note> note;
	
	public long getLabelId() {
		return labelId;
	}
	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<Note> getNote() {
		return note;
	}
	public void setNote(List<Note> note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", modifiedDate=" + modifiedDate + ", labelName=" + labelName
				+ ", createdDate=" + createdDate + ", userId=" + userId + ", note=" + note + "]";
	}
}
	
	