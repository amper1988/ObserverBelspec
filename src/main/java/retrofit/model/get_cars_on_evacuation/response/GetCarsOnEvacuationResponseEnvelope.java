package retrofit.model.get_cars_on_evacuation.response;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import retrofit.model.CarDataShortList;

@Root(name = "Envelope")
public class GetCarsOnEvacuationResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetCarsOnEvacuationResponse")
    private CarDataShortList data;

    public CarDataShortList getData() {
        return this.data;
    }
}
