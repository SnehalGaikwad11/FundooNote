package com.bridgelab.note.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelab.exception.LabelException;
import com.bridgelab.exception.NoteException;
import com.bridgelab.exception.UserException;
import com.bridgelab.note.dto.LabelDto;
import com.bridgelab.note.dto.NoteDto;
import com.bridgelab.note.model.Label;
import com.bridgelab.note.model.Note;
import com.bridgelab.note.repository.LabelRepository;
import com.bridgelab.note.repository.NoteRepository;
import com.bridgelab.response.Response;
import com.bridgelab.user.model.User;
import com.bridgelab.user.repository.UserRepository;
import com.bridgelab.util.StatusHelper;
import com.bridgelab.util.UserToken;

@Service("labelService")
@PropertySource("classpath:message.properties")
public class LabelServiceImplmentation implements ILabelService {
@Autowired
ModelMapper modelMapper;
@Autowired
private Environment environment;
@Autowired
private LabelRepository labelRepository;

@Autowired 
private UserToken userToken;
@Autowired
private NoteRepository noteRepository;
@Autowired
private UserRepository userRepository;

	
	
	
	
@Override
public Response createLabel(LabelDto labelDto, String token) {
	long userId= UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent())
	{
		throw new LabelException("user not present",-6);
	}
	if(labelDto.getLabelName().isEmpty())
			{
				throw new LabelException("label not present",-6);
			}
	Optional<Label> labelPresent = labelRepository.findByUserIdAndLabelName(userId,labelDto.getLabelName());
	if(labelPresent.isPresent())
	{
		throw new LabelException ("label already exist",-6);
	}
	
	
	Label label = modelMapper.map(labelDto,Label.class);

	label.setLabelName(labelDto.getLabelName());
	label.setUserId(userId);
	label.setCreatedDate(LocalDateTime.now());
	label.setModifiedDate(LocalDateTime.now());
	labelRepository.save(label);
	user.get().getLabel().add(label);
	userRepository.save(user.get());
	Response response = StatusHelper.statusInfo(environment.getProperty("status.label.created"), Integer.parseInt(environment.getProperty("status.success.code")));
	return response;
	
}
@Override
public Response updateLabel(LabelDto labelDto, String token, long labelId) {
	long userId= UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent())
	{
		throw new LabelException("user not present",-6);
	}
	Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
	if(label==null)
	{
		throw new LabelException("no label present",-6);
	}
	if(labelDto.getLabelName().isEmpty())
			{
				throw new LabelException("label has no name",-6);
			}
	
	Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
	if(labelAvailability.isPresent()) {
		throw new LabelException("Label already exist", -6);
	}
	label.setLabelName(labelDto.getLabelName());
	label.setModifiedDate(LocalDateTime.now());
	labelRepository.save(label);
	Response response = StatusHelper.statusInfo(environment.getProperty("status.label.updated"), Integer.parseInt(environment.getProperty("status.success.code")));
	return response;

}

@Override
public Response deleteLabel(String token, long labelId) {
	long userId = UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent()) {
		throw new LabelException("Invalid input", -6);
	}
	Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
	if(label == null) {
		throw new LabelException("Invalid input", -6);
	}
	labelRepository.delete(label);
	Response response = StatusHelper.statusInfo(environment.getProperty("status.label.deleted"), Integer.parseInt(environment.getProperty("status.success.code")));
	return response;

	
}

public List<LabelDto> getAllLabel(String token)

{
	long userId = UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent())
	{
		throw new UserException("user is not present", -6);
	}
	List<Label> labels = labelRepository.findByUserId(userId);
	List<LabelDto> listLabel = new ArrayList<>();
	for(Label noteLabel : labels) {
		LabelDto labelDto = modelMapper.map(noteLabel, LabelDto.class);
		listLabel.add(labelDto);
	}
	return listLabel;
}

public List<NoteDto> getNotesOfLabel(String token, long labelId) {
	long userId = UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent()) {
		throw new LabelException("Invalid input", -6);
	}
	Optional<Label> label = labelRepository.findById(labelId);
	if(!label.isPresent()) {
		throw new LabelException("No lebel exist", -6);
	}
	List<Note> notes = label.get().getNote();
	List<NoteDto> listNotes = new ArrayList<>();
	for (Note usernotes : notes) {
		NoteDto noteDto = modelMapper.map(usernotes, NoteDto.class);
		listNotes.add(noteDto);
	}
	return listNotes;
}
	

@Override
public List<LabelDto> getLebelsOfNote(String token, long noteId) {
	long userId = UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent()) {
		throw new UserException("User does not exist", -6);
	}
	Optional<Note> note = noteRepository.findById(noteId);
	if(!note.isPresent()) { 
		throw new NoteException("Note does not exist", -6);
	}
	List<Label> lebel = note.get().getListLabel();
	
	List<LabelDto> listLabel = new ArrayList<>();
	for(Label noteLabel : lebel) {
		LabelDto labelDto = modelMapper.map(noteLabel, LabelDto.class);
		listLabel.add(labelDto);
	}
	return listLabel;
	
}
@Override
public Response addLabelToNote(long labelId, String token , long noteId) {
	long userId = UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent()) {
		throw new LabelException("Invalid input", -6);
	}
	Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
	if(label == null) {
		throw new LabelException("No such lebel exist", -6);
	}
	Note note =  noteRepository.findByIdAndUserId(noteId, userId);
	if(note == null) {
		throw new LabelException("No such note exist", -6);
	}
	label.setModifiedDate(LocalDateTime.now());
	label.getNote().add(note);
	note.getListLabel().add(label);
	note.setModified(LocalDateTime.now());
	labelRepository.save(label);
	noteRepository.save(note);
	Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"), Integer.parseInt(environment.getProperty("status.success.code")));
	return response;
}
@Override
public Response removeLabelFromNote(long labelId ,String token , long noteId) {
	long userId = UserToken.tokenVerify(token);
	Optional<User> user = userRepository.findById(userId);
	if(!user.isPresent()) {
		throw new LabelException("Invalid input", -6);
	}
	Label label = labelRepository.findByLabelIdAndUserId(labelId , userId);
	if(label == null) {
		throw new LabelException("No such lebel exist", -6);
	}
	Note note =  noteRepository.findByIdAndUserId(noteId, userId);
	if(note == null) {
		throw new LabelException("No such note exist", -6);
	}
	label.setModifiedDate(LocalDateTime.now());
	note.getListLabel().remove(label);
	note.setModified(LocalDateTime.now());
	labelRepository.save(label);
	noteRepository.save(note);
	Response response = StatusHelper.statusInfo(environment.getProperty("status.label.removedfromnote"), Integer.parseInt(environment.getProperty("status.success.code")));
	return response;
}
	
}
