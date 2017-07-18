package retrofit.model.get_car_details.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import retrofit.model.CarDataFull;

@Root(name = "Envelope")
public class GetCarDetailsResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;

    @Element(name = "return")
    @Path("Body/GetCarDetailsResponse")
    private CarDataFull carDataFull;

    public CarDataFull getCarDataFull() {
        return this.carDataFull;
    }
}
