package be.soldier.money.web.servlet.get;

import be.soldier.money.common.ConvertUtil;
import be.soldier.money.persistence.dao.AccountDao;
import be.soldier.money.persistence.dao.NoteDao;
import be.soldier.money.web.dto.JsonResult;
import be.soldier.money.web.dto.Status;
import be.soldier.money.web.servlet.template.AbstractJsonGetObjectServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(name = "JsonGetNotesServlet" , displayName = "JsonGetNotesServlet", urlPatterns = { "/JsonGetNotes" })
public class JsonGetNotesServlet extends AbstractJsonGetObjectServlet {
    private static final long serialVersionUID = 1L;

    private NoteDao noteDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        noteDao = (NoteDao) springContext.getBean("noteDao");
    }

    @Override
    protected JsonResult getJsonResult(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        result.setOutput(noteDao.findNotes());
        result.setStatus(Status.OK);
        return result;
    }

}