package retrofit.model.get_cars_on_parking.response;

import org.simpleframework.xml.*;
import retrofit.model.CarDataShortList;

@Root(name = "Envelope")
public class GetCarsOnParkingResponseEnvelope {
    @Element(name = "Header", required = false)
    private String header;


    @Element(name = "return")
    @Path("Body/GetCarsOnParkingResponse")
    private CarDataShortList data;

    public CarDataShortList getData() {
        return this.data;
    }

}

