package pet.store;

import java.util.Objects;

public class ResponseMessage {
    private int code;
    private String type;
    private String message;

    public final void setCode(final int code) {
        this.code = code;
    }

    public final int getCode() {
        return code;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final String getType() {
        return type;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }

    public final String getMessage() {
        return message;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseMessage)) {
            return false;
        }
        ResponseMessage error = (ResponseMessage) o;
        return code == error.code && Objects.equals(type, error.type) && Objects.equals(message, error.message);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(code, type, message);
    }
}
