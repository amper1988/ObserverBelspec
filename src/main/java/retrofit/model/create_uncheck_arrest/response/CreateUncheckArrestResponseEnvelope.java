package retrofit.model.create_uncheck_arrest.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import retrofit.model.ServerAnswer;

@Root(name = "Envelope")
public class CreateUncheckArrestResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/CreateUncheckArrestResponse")
    private ServerAnswer serverAnswer;

    public ServerAnswer getServerAnswer() {
        return this.serverAnswer;
    }
}
