package retrofit.model.get_cars_on_parking.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetCarsOnParkingRequestEnvelope {
    @Element(name = "tes:GetCarsOnParking")
    @Path("soap12:Body")
    private GetCarsOnParkingRequestData data;

    public GetCarsOnParkingRequestEnvelope(int page, String param) {
        this.data = new GetCarsOnParkingRequestData(page, param);
    }


}
