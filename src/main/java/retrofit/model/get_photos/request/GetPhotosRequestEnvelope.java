package retrofit.model.get_photos.request;

import org.simpleframework.xml.*;

@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "tes", reference = "www.uri.com"),
        @Namespace(prefix = "soap12", reference = "http://schemas.xmlsoap.org/soap/envelope/")
})
public class GetPhotosRequestEnvelope {
    @Element(name = "tes:GetPhotos")
    @Path("soap12:Body")
    private GetPhotosRequestData data;

    public GetPhotosRequestEnvelope(int identifier) {
        this.data = new GetPhotosRequestData(identifier);
    }
}
