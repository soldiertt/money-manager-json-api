package be.soldier.money.web.servlet.delete;

import be.soldier.money.model.MoneyLabel;
import be.soldier.money.persistence.dao.LabelDao;
import be.soldier.money.persistence.dao.TransactionDao;
import be.soldier.money.persistence.jooq.tables.records.TxRecord;
import be.soldier.money.web.servlet.template.AbstractAddObjectServlet;
import be.soldier.money.web.servlet.template.AbstractDeleteObjectServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Calendar;


@WebServlet(name = "DeleteTransactionServlet" , displayName = "DeleteTransactionServlet", urlPatterns = { "/DeleteTransaction" })
public class DeleteTransactionServlet extends AbstractDeleteObjectServlet {
    private static final long serialVersionUID = 1L;

    private TransactionDao transactionDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        transactionDao = (TransactionDao) springContext.getBean("transactionDao");
    }

    @Override
    protected void deleteObject(Integer objectId) throws IOException {
        transactionDao.deleteTransaction(objectId);
    }
}