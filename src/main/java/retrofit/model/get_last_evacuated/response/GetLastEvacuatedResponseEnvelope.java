package retrofit.model.get_last_evacuated.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import retrofit.model.CarDataShortList;

@Root(name = "Envelope")
public class GetLastEvacuatedResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetLastEvacuatedResponse")
    private CarDataShortList data;

    public CarDataShortList getData(){
        return data;
    }
}
