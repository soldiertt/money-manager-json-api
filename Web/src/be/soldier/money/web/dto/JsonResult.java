package be.soldier.money.web.dto;

import java.util.List;

/**
 * Created by soldiertt on 31-01-15.
 */
public class JsonResult {

    private Status status;

    private String errorMessage;

    private Object output;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
