package be.soldier.money.web.servlet.get;

import be.soldier.money.persistence.dao.TransactionDao;
import be.soldier.money.web.dto.JsonResult;
import be.soldier.money.web.dto.Status;
import be.soldier.money.web.servlet.template.AbstractJsonGetObjectServlet;
import be.soldier.money.web.util.NumberUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


@WebServlet(name = "JsonGetTransactionsServlet" , displayName = "JsonGetTransactionsServlet", urlPatterns = { "/JsonGetTransactions" })
public class JsonGetTransactionsServlet extends AbstractJsonGetObjectServlet {
    private static final long serialVersionUID = 1L;

    private TransactionDao transactionDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        transactionDao = (TransactionDao) springContext.getBean("transactionDao");
    }

    @Override
    protected JsonResult getJsonResult(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        final String labelIdParam = request.getParameter("labelId");
        final String labelIndexParam = request.getParameter("index");
        if (NumberUtils.isNumericInt(labelIdParam)) {
            if (NumberUtils.isNumericInt(labelIndexParam)) {
                result.setOutput(transactionDao.findOccurenceByLabelIdAndIndex(NumberUtils.toInt(labelIdParam), NumberUtils.toInt(labelIndexParam)));
            } else {
                result.setOutput(transactionDao.findOccurencesByLabelId(NumberUtils.toInt(labelIdParam)));
            }
            result.setStatus(Status.OK);
        } else {
            result.setStatus(Status.ERROR);
            result.setErrorMessage("Invalid arguments");
        }
        return result;
    }

}