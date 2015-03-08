package be.soldier.money.web.servlet.template;

import be.soldier.money.web.dto.JsonResult;
import be.soldier.money.web.dto.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public abstract class AbstractDeleteObjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AbstractDeleteObjectServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Not implemented
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonResult result = new JsonResult();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if (br != null){
            json = br.readLine();
        }
        JsonElement body = new JsonParser().parse(json);
        JsonObject bodyObject = body.getAsJsonObject();
        String objectId = bodyObject.get("id").toString();
        try {
            deleteObject(Integer.parseInt(objectId));
            result.setStatus(Status.OK);
        } catch (Exception e) {
            result.setErrorMessage("An error occured during transaction deletion.");
            result.setStatus(Status.ERROR);
        }
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        Gson gson = new GsonBuilder().create();
        out.print(gson.toJson(result));
    }

    protected abstract void deleteObject(Integer objectId) throws IOException;
}