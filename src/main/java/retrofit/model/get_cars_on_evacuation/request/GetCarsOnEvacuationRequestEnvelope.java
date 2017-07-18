package retrofit.model.get_cars_on_evacuation.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetCarsOnEvacuationRequestEnvelope {
    @Element(name = "tes:GetCarsOnEvacuation")
    @Path("soap12:Body")
    private GetCarsOnEvacuationRequestData data;

    public GetCarsOnEvacuationRequestEnvelope(int page, String param) {
        this.data = new GetCarsOnEvacuationRequestData(page, param);
    }
}
