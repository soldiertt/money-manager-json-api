package be.soldier.money.web.servlet.add;

import be.soldier.money.persistence.dao.LabelDao;
import be.soldier.money.persistence.dao.NoteDao;
import be.soldier.money.persistence.jooq.tables.records.LabelRecord;
import be.soldier.money.persistence.jooq.tables.records.NotesRecord;
import be.soldier.money.web.servlet.template.AbstractAddObjectServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


@WebServlet(name = "AddNoteServlet" , displayName = "AddNoteServlet", urlPatterns = { "/AddNote" })
public class AddNoteServlet extends AbstractAddObjectServlet {
    private static final long serialVersionUID = 1L;

    private NoteDao noteDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        noteDao = (NoteDao) springContext.getBean("noteDao");
    }

    @Override
    protected Object saveJsonAsObject(ObjectMapper mapper, String json) throws IOException {
        NotesRecord note = mapper.readValue(json, NotesRecord.class);

        noteDao.saveNote(note);

        return null;
    }
}