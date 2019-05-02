package com.bridgelab.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.note.dto.LabelDto;
import com.bridgelab.note.dto.NoteDto;
import com.bridgelab.note.service.ILabelService;
import com.bridgelab.response.Response;

@RestController
@RequestMapping("/user/label")
@PropertySource("classpath:message.properties")
public class LabelController {

	
	@Autowired
	ILabelService labelService;
	
@PostMapping("/create")
ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto , @RequestParam  String token)
{
	Response response= labelService.createLabel(labelDto,token);
	return new ResponseEntity<Response>(response,HttpStatus.ACCEPTED);
	
}

@PutMapping("/update")
ResponseEntity<Response> updateLabel(@RequestBody LabelDto labelDto, @RequestParam String token ,@RequestParam long labelId)
{
	Response response = labelService.updateLabel(labelDto,token,labelId);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	
}

@DeleteMapping("/delete")
ResponseEntity<Response> deleteLabel(@RequestParam String token ,@RequestParam long labelId)
{
	Response response = labelService.deleteLabel(token,labelId);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
}
	
@GetMapping("/getlabel")
List<LabelDto> getLabel(@RequestHeader String token){
	List<LabelDto> listLabel = labelService.getAllLabel(token);
	return listLabel;
}

@PutMapping("/addlabeltonote")
ResponseEntity<Response> addNoteToLebel(@RequestParam long labelId , @RequestHeader String token , @RequestParam long noteId){
	Response statusResponse = labelService.addLabelToNote(labelId, token, noteId);
	return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
}

@GetMapping("/getlabelofnote")
List<LabelDto> getLebelOfNote(@RequestHeader String token, @RequestParam long noteId){
	List<LabelDto> listLabel = labelService.getLebelsOfNote(token, noteId);
	return listLabel;
}
@PutMapping("/removefromnote")
ResponseEntity<Response> removeFromNote(@RequestHeader String token, @RequestParam long noteId , @RequestParam long labelId){
	Response statusResponse = labelService.removeLabelFromNote(labelId, token, noteId);
	return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
}
@GetMapping("/getnotesoflabel")
List<NoteDto> getNotesOfLabel(@RequestHeader String token , @RequestParam long labelId){
	List<NoteDto> listNotes = labelService.getNotesOfLabel(token, labelId);
	return listNotes;
}

}
