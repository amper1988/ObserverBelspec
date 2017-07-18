package retrofit.model.test_connection.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class TestRequestEnvelope {
    @Element(name = "tes:ConfirmConnections")
    @Path("soap12:Body")
    public String body;

    public TestRequestEnvelope() {
        this.body = "";
    }
}