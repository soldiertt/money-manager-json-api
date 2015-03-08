package be.soldier.money.web.servlet.template;

import be.soldier.money.web.dto.JsonResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public abstract class AbstractJsonGetObjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AbstractJsonGetObjectServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        JsonResult result = getJsonResult(request);
        out.print(gson.toJson(result));
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Not implemented
    }

    protected abstract JsonResult getJsonResult(HttpServletRequest request);
}