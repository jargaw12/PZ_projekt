package weatherModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Error implements Serializable {
    @JsonProperty
    private int code;

    @JsonProperty
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int mCode) {
        code = mCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mMessage) {
        message = mMessage;
    }
}
