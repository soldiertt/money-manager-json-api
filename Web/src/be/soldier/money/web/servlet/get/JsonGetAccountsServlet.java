package be.soldier.money.web.servlet.get;

import be.soldier.money.common.ConvertUtil;
import be.soldier.money.common.LabelType;
import be.soldier.money.persistence.dao.AccountDao;
import be.soldier.money.persistence.dao.LabelDao;
import be.soldier.money.web.dto.JsonResult;
import be.soldier.money.web.dto.Status;
import be.soldier.money.web.servlet.template.AbstractJsonGetObjectServlet;
import be.soldier.money.web.util.NumberUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(name = "JsonGetAccountsServlet" , displayName = "JsonGetAccountsServlet", urlPatterns = { "/JsonGetAccounts" })
public class JsonGetAccountsServlet extends AbstractJsonGetObjectServlet {
    private static final long serialVersionUID = 1L;

    private AccountDao accountDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        accountDao = (AccountDao) springContext.getBean("accountDao");
    }

    @Override
    protected JsonResult getJsonResult(HttpServletRequest request) {
        final String ownParam = request.getParameter("own");
        boolean own = ConvertUtil.toBool(ownParam);
        JsonResult result = new JsonResult();
        result.setOutput(accountDao.findAccounts(own));
        result.setStatus(Status.OK);
        return result;
    }

}