package projet.uf.modules.template.exception;

public class TemplateException extends Exception {
    public TemplateException(String message) {
        super(message);
    }

    public TemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}

