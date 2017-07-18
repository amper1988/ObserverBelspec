package retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return")
public class ServerAnswer {
    @Element(name = "Code")
    private int code;
    @Element(name = "Description")
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
