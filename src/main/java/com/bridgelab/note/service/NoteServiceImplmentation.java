package com.bridgelab.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelab.exception.NoteException;
import com.bridgelab.note.dto.NoteDto;
import com.bridgelab.note.model.Note;
import com.bridgelab.note.repository.NoteRepository;
import com.bridgelab.response.Response;

import com.bridgelab.user.model.User;
import com.bridgelab.user.repository.UserRepository;


import java.time.LocalDateTime;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bridgelab.util.StatusHelper;
import com.bridgelab.util.UserToken;

@Service("noteService")
@PropertySource("classpath:message.properties")
public class NoteServiceImplmentation implements INoteservice {

	Logger logger = LoggerFactory.getLogger(NoteServiceImplmentation.class);
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NoteRepository notesRepository;
	
	@Autowired
	private Environment environment;
	
	
	
	@Override
	public Response createNote(NoteDto notesDto, String token) {
		
		long userid = UserToken.tokenVerify(token);
		logger.info(notesDto.toString());
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			
			throw new NoteException("Title and description are empty", -5);

		}
		Note notes = modelMapper.map(notesDto, Note.class);
		Optional<User> user = userRepository.findById(userid);
		notes.setUserId(userid);
		notes.setCreated(LocalDateTime.now());
		notes.setModified(LocalDateTime.now());
		user.get().getNotes().add(notes);
		notesRepository.save(notes);
		userRepository.save(user.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.createdSuccessfull"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	
	@Override
	public Response updateNote(NoteDto notesDto , String token , long noteId) {
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new NoteException("Title and description are empty", -5);
		}
		
		long id = UserToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		notes.setTitle(notesDto.getTitle());
		notes.setDescription(notesDto.getDescription());
		notes.setModified(LocalDateTime.now());
		notesRepository.save(notes);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.updated"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	
	@Override
	public Response delete(String token, long noteId) {
		long id = UserToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NoteException("Invalid input", -5);
		}
		if(notes.isTrash() == false) {
			notes.setTrash(true);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.deletesuccessfully"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.deleteerror"),Integer.parseInt(environment.getProperty("status.note.errorCode")));
		return response;
	}


	
	@Override
	public Response pinAndUnPin(String token, long noteId) {
		long id = UserToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NoteException("Invalid input", -5);
		}
		if(notes.isPin() == false) {
			notes.setPin(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.pinsuccessfully"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setPin(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.unpinsuccessfully"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	
	@Override
	public Response archiveAndUnArchive(String token, long noteId) {
		long id = UserToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NoteException("Invalid input", -5);
		}
		if(notes.isArchive() == false) {
			notes.setArchive(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.archivesucessfully"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setArchive(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unarchivesucessfull"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	
	@Override
	public Response trashAndUnTrash(String token, long noteId) {
		long id = UserToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes.isTrash() == false) {
			notes.setTrash(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashsucessfully"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setTrash(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.untrashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	
	
}

	
