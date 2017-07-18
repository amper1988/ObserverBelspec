package retrofit.model.check_update_observer.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class CheckUpdateObserverRequestEnvelope {
    @Element(name = "soap12:Header", required = false)
    private String header;

    @Element(name = "tes:version")
    @Path("soap12:Body/tes:CheckUpdate")
    private String version;

    public CheckUpdateObserverRequestEnvelope(String version) {
        this.version = version;
    }
}
