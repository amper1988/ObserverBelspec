package retrofit.model.get_image_full.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import retrofit.model.ServerAnswer;

@Root(name = "Envelope")
public class GetImageFullResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetImageFullResponse")
    private ServerAnswer answer;

    public ServerAnswer getAnswer() {
        return this.answer;
    }
}
