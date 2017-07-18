package retrofit.model.get_image_full.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetImageFullRequestEnvelope {
    @Element(name = "tes:GetImageFull")
    @Path("soap12:Body")
    private GetImageFullRequestData data;

    public GetImageFullRequestEnvelope(int identifier, int index) {
        data = new GetImageFullRequestData(identifier, index);
    }
}
