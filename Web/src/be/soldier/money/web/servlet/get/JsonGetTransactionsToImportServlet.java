package be.soldier.money.web.servlet.get;

import be.soldier.money.importation.dto.ArgentaTransactionDto;
import be.soldier.money.importation.dto.BelfiusTransactionDto;
import be.soldier.money.importation.service.CsvService;
import be.soldier.money.importation.service.TransactionFilterService;
import be.soldier.money.importation.service.TransactionMapperService;
import be.soldier.money.model.Transaction;
import be.soldier.money.web.dto.JsonResult;
import be.soldier.money.web.dto.Status;
import be.soldier.money.web.servlet.template.AbstractJsonGetObjectServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "JsonGetTransactionsToImportServlet" , displayName = "JsonGetTransactionsToImportServlet", urlPatterns = { "/JsonGetTransactionsToImport" })
public class JsonGetTransactionsToImportServlet extends AbstractJsonGetObjectServlet {
    private static final long serialVersionUID = 1L;

    private CsvService csvService;
    private TransactionMapperService txMapperService;
    private TransactionFilterService txFilterService;

    @Override
    public void init() throws ServletException {
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        csvService = (CsvService) springContext.getBean("csvService");
        txMapperService = (TransactionMapperService) springContext.getBean("transactionMapperService");
        txFilterService = (TransactionFilterService) springContext.getBean("transactionFilterService");
    }

    @Override
    protected JsonResult getJsonResult(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        List<ArgentaTransactionDto> argentaTxList = csvService.readArgentaTransactions();
        List<BelfiusTransactionDto> belfiusTxList = csvService.readBelfiusTransactions();
        List<Transaction> txListOut = new ArrayList<>();

        txListOut.addAll(txMapperService.mapArgentaTxList(argentaTxList));
        txListOut.addAll(txMapperService.mapBelfiusTxList(belfiusTxList));

        txListOut = txFilterService.filterToImport(txListOut, 10);

        result.setOutput(txListOut);
        result.setStatus(Status.OK);
        return result;
    }

}