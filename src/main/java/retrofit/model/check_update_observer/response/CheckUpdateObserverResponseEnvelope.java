package retrofit.model.check_update_observer.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import retrofit.model.ServerAnswer;

@Root(name = "Envelope")
public class CheckUpdateObserverResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;
    @Element(name = "return")
    @Path("Body/CheckUpdateResponse")
    private ServerAnswer serverAnswer;

    public ServerAnswer getServerAnswer() {
        return serverAnswer;
    }
}
