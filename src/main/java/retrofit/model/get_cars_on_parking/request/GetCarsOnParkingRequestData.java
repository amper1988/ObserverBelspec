package retrofit.model.get_cars_on_parking.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:GetCarsOnParking")
public class GetCarsOnParkingRequestData {
    @Element(name = "tes:page")
    private int page;
    @Element(name = "tes:param")
    private String param;

    public GetCarsOnParkingRequestData(int page, String param) {
        this.page = page;
        this.param = param;
    }
}
