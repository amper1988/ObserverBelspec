package retrofit.model.create_uncheck_arrest.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class CreateUncheckArrestRequestEnvelope {
    @Element(name = "tes:CreateUncheckArrest")
    @Path("soap12:Body")
    private CreateUncheckArrestRequestData data;

    public CreateUncheckArrestRequestEnvelope(int identifier, String uncheckReason) {
        this.data = new CreateUncheckArrestRequestData(identifier, uncheckReason);
    }
}
