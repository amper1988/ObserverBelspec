package retrofit.model.get_car_details.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetCarDetailsRequestEnvelope {
    @Element(name = "tes:GetCarDetails")
    @Path("soap12:Body")
    private GetCarDetailsRequestData data;

    public GetCarDetailsRequestEnvelope(int identifier) {
        this.data = new GetCarDetailsRequestData(identifier);
    }
}
