package be.soldier.money.web.servlet.add;

import be.soldier.money.model.MoneyLabel;
import be.soldier.money.persistence.dao.LabelDao;
import be.soldier.money.persistence.dao.TransactionDao;
import be.soldier.money.persistence.jooq.tables.records.TxRecord;
import be.soldier.money.web.servlet.template.AbstractAddObjectServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Calendar;


@WebServlet(name = "AddTransactionServlet" , displayName = "AddTransactionServlet", urlPatterns = { "/AddTransaction" })
public class AddTransactionServlet extends AbstractAddObjectServlet {
    private static final long serialVersionUID = 1L;

    private TransactionDao transactionDao;
    private LabelDao labelDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        transactionDao = (TransactionDao) springContext.getBean("transactionDao");
        labelDao = (LabelDao) springContext.getBean("labelDao");
    }

    @Override
    protected Object saveJsonAsObject(ObjectMapper mapper, String json) throws IOException {
        TxRecord tx = mapper.readValue(json, TxRecord.class);

        // Compute labelIndex
        MoneyLabel label = labelDao.getLabelById(tx.getLabelId());

        Calendar cal = Calendar.getInstance();
        cal.setTime(tx.getDate());
        if (tx.getDateCompta() != null) {
            cal.setTime(tx.getDateCompta());
        }

        int labelIndex = 1; //YEARLY AS DEFAULT
        int month = cal.get(Calendar.MONTH);
        if (label.getOccSize() == 12) { //MONTHLY
            labelIndex = month + 1;
        } else if (label.getOccSize() == 4) { //QUARTER
            labelIndex = (month / 3) + 1;
        }
        tx.setLabelIndex(labelIndex);

        transactionDao.save(tx);

        return labelIndex;
    }
}