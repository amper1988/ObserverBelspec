package retrofit.model.get_last_evacuated.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetLastEvacuatedRequestEnvelope {
    @Element(name = "tes:GetLastEvacuated")
    @Path("soap12:Body")
    private String body;

    public GetLastEvacuatedRequestEnvelope(){
        body = "";
    }
}
