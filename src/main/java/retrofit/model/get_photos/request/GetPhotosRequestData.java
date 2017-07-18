package retrofit.model.get_photos.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:GetPhotos")
public class GetPhotosRequestData {
    @Element(name = "identifier")
    private int identifier;

    public GetPhotosRequestData(int identifier) {
        this.identifier = identifier;
    }
}
