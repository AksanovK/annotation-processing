public class Form {

    private String method;
    private String action;

    public Form(String method, String action) {
        this.method = method;
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public String getAction() {
        return action;
    }


    public void setMethod(String method) {
        this.method = method;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
