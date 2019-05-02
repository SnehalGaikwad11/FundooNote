package com.bridgelab.user.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

import com.bridgelab.note.model.Label;
import com.bridgelab.note.model.Note;
@Component
@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@Column(name = "name")
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name = "email")
	@Email
	private String email;


	@Column(name = "password")
	private String password;
	
	@Column(name = "mobileNumber")
	
	private String mobileNumber;
	
	@Column(name = "isVerified")
	boolean isVarified;
	
	@Column(name = "registeredDate")
	private LocalDate registeredDate;

	@Column(name = "updatedDate")
	private LocalDate updatedDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Note> notes;

	public List<Note> getNotes() {
		return notes;
	}
	@OneToMany(cascade = CascadeType.ALL)
	private List<Label> label;


	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isVarified() {
		return isVarified;
	}

	public void setVarified(boolean isVarified) {
		this.isVarified = isVarified;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", mobileNumber=" + mobileNumber + ", isVarified=" + isVarified + ", registeredDate=" + registeredDate
				+ ", updatedDate=" + updatedDate + ", notes=" + notes + ", label=" + label + "]";
	}


	

	
}
