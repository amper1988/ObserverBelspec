package retrofit.model.create_uncheck_arrest.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:CreateUncheckArrest")
public class CreateUncheckArrestRequestData {
    @Element(name = "identifier")
    private int identifier;
    @Element(name = "uncheck_reason")
    private String uncheckReason;

    public CreateUncheckArrestRequestData(int identifier, String uncheckReason) {
        this.identifier = identifier;
        this.uncheckReason = uncheckReason;
    }
}
