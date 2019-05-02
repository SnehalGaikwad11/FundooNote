package com.bridgelab.note.service;
import org.springframework.stereotype.Service;
import com.bridgelab.note.dto.NoteDto;
import com.bridgelab.response.Response;

@Service
public interface INoteservice {

	public Response createNote(NoteDto notesDto , String token);
	
	public Response updateNote(NoteDto notesDto,String token,long noteId);
	
	public Response delete(String token , long noteId);
	

	
	public Response pinAndUnPin(String token , long noteId);
	
	public Response archiveAndUnArchive(String token , long noteId);
	
	public Response trashAndUnTrash(String token , long noteId);
	
	
}