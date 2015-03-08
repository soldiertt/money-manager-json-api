package be.soldier.money.web.servlet.add;

import be.soldier.money.persistence.dao.LabelDao;
import be.soldier.money.persistence.dao.TransactionDao;
import be.soldier.money.persistence.jooq.tables.records.LabelRecord;
import be.soldier.money.persistence.jooq.tables.records.TxRecord;
import be.soldier.money.web.servlet.template.AbstractAddObjectServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


@WebServlet(name = "AddLabelServlet" , displayName = "AddLabelServlet", urlPatterns = { "/AddLabel" })
public class AddLabelServlet extends AbstractAddObjectServlet {
    private static final long serialVersionUID = 1L;

    private LabelDao labelDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        labelDao = (LabelDao) springContext.getBean("labelDao");
    }

    @Override
    protected Object saveJsonAsObject(ObjectMapper mapper, String json) throws IOException {
        LabelRecord label = mapper.readValue(json, LabelRecord.class);

        labelDao.saveLabel(label);

        return null;
    }
}