package retrofit.model;


import org.simpleframework.xml.*;

import java.util.List;


public class CarDataShortList {

    @ElementList(entry = "CarDataShort", inline = true, required = false)
    private List<CarDataShort> carDataShortList;

    public List<CarDataShort> getCarDataShortList() {
        return this.carDataShortList;
    }

}
