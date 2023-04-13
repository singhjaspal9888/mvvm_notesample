package com.cheezycode.notesample.repository

import com.cheezycode.notesample.api.NoteDao
import com.cheezycode.notesample.models.NoteEntity
import javax.inject.Inject

class DbRepository @Inject constructor(private val dao:  NoteDao,) {

    fun saveNote(note : NoteEntity) = dao.inserNote(note)
    fun updateNote(note: NoteEntity) = dao.updateNote(note)
    fun deleteNote(note: NoteEntity) = dao.deleteNote(note)
    fun getNote(id : Int) : NoteEntity = dao.getNote(id)
    fun getAllNotes() = dao.getAllNotes()
}