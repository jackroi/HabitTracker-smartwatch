package it.unive.rosin.habittrackersmartwatch.api.http.response;

public class ResponseBody {
    private int statusCode;
    private boolean error;
    private String errorMessage;

    public ResponseBody() { }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
