package com.bridgelab.note.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelab.note.dto.LabelDto;
import com.bridgelab.note.dto.NoteDto;
import com.bridgelab.response.Response;
@Service
public interface ILabelService {

	public Response createLabel(LabelDto labelDto, String token);

	public Response updateLabel(LabelDto labelDto, String token, long labelId);

	public Response deleteLabel(String token, long labelId);
	
	public List<LabelDto> getAllLabel(String token);
	public List<NoteDto> getNotesOfLabel(String token, long labelId);
	public List<LabelDto> getLebelsOfNote(String token, long noteId);
	public Response addLabelToNote(long labelId, String token , long noteId) ;

	public 	Response removeLabelFromNote(long labelId, String token, long noteId);

	

}