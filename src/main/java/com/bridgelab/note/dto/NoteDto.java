package com.bridgelab.note.dto;

public class NoteDto {
	
	private String title;
	private String description;
	
	public NoteDto() {
		super();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "NotesDto [title=" + title + ", description=" + description + "]";
	}
	
	
}