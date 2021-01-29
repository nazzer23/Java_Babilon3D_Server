package Game.Exceptions;

public class DatabaseSQLException extends Exception {

    private static final long serialVersionUID = 1L;
    private String type = "warning";

    public DatabaseSQLException() {
        super();
    }

    public DatabaseSQLException(String msg) {
        super(msg);
    }

    public DatabaseSQLException(String msg, String type) {
        super(msg);
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
