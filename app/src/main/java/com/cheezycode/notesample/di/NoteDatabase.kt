package com.cheezycode.notesample.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cheezycode.notesample.api.NoteDao
import com.cheezycode.notesample.models.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract  fun noteDoa(): NoteDao
}