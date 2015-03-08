package be.soldier.money.web.servlet.delete;

import be.soldier.money.persistence.dao.NoteDao;
import be.soldier.money.persistence.dao.TransactionDao;
import be.soldier.money.web.servlet.template.AbstractDeleteObjectServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


@WebServlet(name = "DeleteNoteServlet" , displayName = "DeleteNoteServlet", urlPatterns = { "/DeleteNote" })
public class DeleteNoteServlet extends AbstractDeleteObjectServlet {
    private static final long serialVersionUID = 1L;

    private NoteDao noteDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        noteDao = (NoteDao) springContext.getBean("noteDao");
    }

    @Override
    protected void deleteObject(Integer objectId) throws IOException {
        noteDao.deleteNote(objectId);
    }
}