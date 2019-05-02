package com.bridgelab.note.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.bridgelab.note.model.Note;
@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{
	/**
	 * Purpose : Get note by userid and noteid
	 * @param id
	 * @param userId
	 * @return
	 */
	public Note findByIdAndUserId(long id , long userId);
	/**
	 * Purpose : Get list of notes of user
	 * @param id
	 * @return
	 */
	public List<Note> findByUserId(long id);
	
	
}