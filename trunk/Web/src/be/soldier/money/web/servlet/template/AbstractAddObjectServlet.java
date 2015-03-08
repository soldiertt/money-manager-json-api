package be.soldier.money.web.servlet.template;

import be.soldier.money.persistence.dao.TransactionDao;
import be.soldier.money.persistence.jooq.tables.records.TxRecord;
import be.soldier.money.web.dto.JsonResult;
import be.soldier.money.web.dto.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;


public abstract class AbstractAddObjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AbstractAddObjectServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Not implemented
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonResult result = new JsonResult();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String json = "";
        if (br != null){
            json = br.readLine();
        }

        ObjectMapper mapper = new ObjectMapper();

        result.setOutput(saveJsonAsObject(mapper, json));
        result.setStatus(Status.OK);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        out.print(gson.toJson(result));
    }

    protected abstract Object saveJsonAsObject(ObjectMapper mapper, String json) throws IOException;
}