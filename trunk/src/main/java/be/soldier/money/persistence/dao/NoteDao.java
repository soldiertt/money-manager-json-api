package be.soldier.money.persistence.dao;

import be.soldier.money.common.ConvertUtil;
import be.soldier.money.model.Account;
import be.soldier.money.model.Note;
import be.soldier.money.persistence.jooq.tables.records.AccountRecord;
import be.soldier.money.persistence.jooq.tables.records.NotesRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static be.soldier.money.persistence.jooq.tables.Notes.NOTES;
import static be.soldier.money.persistence.jooq.tables.Tx.TX;

/**
 * Created by soldiertt on 29-01-15.
 */
@Repository("noteDao")
public class NoteDao  {

    @Autowired
    private DSLContext create;

    public List<Note> findNotes() {

        List<Note> notes = new ArrayList<>();
        Result<NotesRecord> result = create.selectFrom(NOTES).fetch();

        for (NotesRecord record : result) {
            Note note = new Note(record.getId(), record.getContent());
            notes.add(note);
        }
        return notes;
    }


    @Transactional
    public Note saveNote(NotesRecord note) {

        Record record = create.insertInto(NOTES,
                NOTES.CONTENT)
                .values(note.getContent())
                .returning(NOTES.ID).fetchOne();
        Integer noteId = record.getValue(NOTES.ID);
        return new Note(noteId, note.getContent());
    }

    @Transactional
    public void deleteNote(Integer noteId) {
        create.delete(NOTES).where(NOTES.ID.equal(noteId)).execute();
    }
}

