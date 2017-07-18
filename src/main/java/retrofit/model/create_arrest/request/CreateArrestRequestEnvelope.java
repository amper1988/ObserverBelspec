package retrofit.model.create_arrest.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class CreateArrestRequestEnvelope {
    @Element(name = "tes:CreateArrest")
    @Path("soap12:Body")
    private CreateArrestRequestData data;

    public CreateArrestRequestEnvelope(int identifier, String whoArrested, String arrestReason) {
        this.data = new CreateArrestRequestData(identifier, whoArrested, arrestReason);
    }
}
