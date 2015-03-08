package be.soldier.money.web.servlet.get;

import be.soldier.money.common.ConvertUtil;
import be.soldier.money.common.LabelCriteria;
import be.soldier.money.common.LabelType;
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

@WebServlet(name = "JsonGetLabelsServlet" , displayName = "JsonGetLabelsServlet", urlPatterns = { "/JsonGetLabels" })
public class JsonGetLabelsServlet extends AbstractJsonGetObjectServlet {
    private static final long serialVersionUID = 1L;

    private LabelDao labelDao;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        labelDao = (LabelDao) springContext.getBean("labelDao");
    }

    @Override
    protected JsonResult getJsonResult(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        final String yearParam = request.getParameter("year"); //REQUIRED
        final String fixedParam = request.getParameter("fixed"); //OPTIONAL
        final String incomeParam = request.getParameter("income"); //OPTIONAL
        final String typeParam = request.getParameter("type"); //OPTIONAL

        // VALIDATION DES REQUIRED
        if (!NumberUtils.isNumericInt(yearParam)) {
            result.setErrorMessage("Invalid arguments - Year is not numeric");
        }
        // VALIDATION DES OPTIONAL
        if ((fixedParam != null && !ConvertUtil.isBoolean(fixedParam))
            || (incomeParam != null && !ConvertUtil.isBoolean(incomeParam))) {
            result.setErrorMessage("Invalid arguments - Invalid boolean value");
        }
        LabelType labelType = null;
        if (typeParam != null) {
            try {
                labelType = LabelType.valueOf(typeParam);
            } catch (IllegalArgumentException e) {
                result.setErrorMessage("Invalid arguments - Invalid value for type");
            }
        }

        // MANAGE VALIDATION ERROR
        if (result.getErrorMessage() != null) {
            result.setStatus(Status.ERROR);
            return result;
        }

        LabelCriteria criteria = new LabelCriteria();
        criteria.setYear(NumberUtils.toInt(yearParam));
        if (fixedParam != null) {
            criteria.setFixed(ConvertUtil.toBool(fixedParam));
        }
        if (incomeParam != null) {
            criteria.setIncome(ConvertUtil.toBool(incomeParam));
        }
        criteria.setLabelType(labelType);

        result.setOutput(labelDao.findMoneyLabels(criteria));
        result.setStatus(Status.OK);
        return result;
    }

}