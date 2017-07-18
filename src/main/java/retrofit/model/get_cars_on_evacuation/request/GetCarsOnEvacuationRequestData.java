package retrofit.model.get_cars_on_evacuation.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tes:GetCarsOnEvacuation")
public class GetCarsOnEvacuationRequestData {
    @Element(name = "tes:page")
    private int page;
    @Element(name = "tes:param")
    private String param;

    GetCarsOnEvacuationRequestData(int page, String param) {
        this.page = page;
        this.param = param;
    }
}
