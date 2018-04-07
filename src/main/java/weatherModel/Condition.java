package weatherModel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String text;

    @JsonProperty
    private String icon;

    @JsonProperty
    private int code;

    public String getText() {
        return text;
    }

    public void setText(String mText) {
        this.text = mText;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String mIcon) {
        this.icon = mIcon;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int mCode) {
        this.code = mCode;
    }

}
