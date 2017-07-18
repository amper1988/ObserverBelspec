package retrofit.model.create_arrest.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:CreateArrest")
public class CreateArrestRequestData {
    @Element(name = "identifier")
    private int identifier;
    @Element(name = "who_arrested")
    private String whoArrested;
    @Element(name = "arrest_reason")
    private String arrestReason;

    public CreateArrestRequestData(int identifier, String whoArrested, String arrestReason) {
        this.identifier = identifier;
        this.whoArrested = whoArrested;
        this.arrestReason = arrestReason;
    }
}
