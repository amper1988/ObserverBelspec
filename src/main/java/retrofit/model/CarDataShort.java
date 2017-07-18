package retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "CarDataShort")
public class CarDataShort {
    @Element(name = "identifier")
    private int identifier;
    @Element(name = "manufacture")
    private String manufacture;
    @Element(name = "model", required = false)
    private String model;
    @Element(name = "car_id", required = false)
    private String carId;
    @Element(name = "clause", required = false)
    private String clause;
    @Element(name = "protocol_number", required = false)
    private String protocolNumber;
    @Element(name = "parking_date", required = false)
    private String parkingDate;
    @Element(name = "evacuation_date", required = false)
    private String evacuationDate;
    @Element(name = "police_department", required = false)
    private String policeDepartment;
    @Element(name = "block", required = false)
    private boolean block;
    @Element(name = "road_law_point", required = false)
    private String roadLawPoint;
    @Element(name = "police_officer", required = false)
    private String policeOfficer;
    @Element(name = "parking", required = false)
    private String parking;
    @Element(name = "evacuation_address", required = false)
    private String evacuationAddress;

    public String getEvacuationAddress() {
        if(evacuationAddress != null)
            return evacuationAddress;
        return "";
    }

    public String getParking() {
        if(parking != null)
            return parking;
        return "";
    }

    public String getRoadLawPoint() {
        if(roadLawPoint!= null)
            return roadLawPoint;
        return "";
    }

    public String getPoliceOfficer() {
        if(policeOfficer != null)
            return policeOfficer;
        return "";
    }

    public int getIdentifier() {
            return identifier;
    }

    public String getManufacture() {
        if(manufacture != null)
            return manufacture;
        return "";
    }

    public String getModel() {
        if(model != null)
            return model;
        return "";
    }

    public String getCarId() {
        if(carId != null)
            return carId;
        return "";
    }

    public String getClause() {
        if(clause!= null)
            return clause;
        return "";
    }

    public String getProtocolNumber() {
        if(protocolNumber != null)
            return protocolNumber;
        return "";
    }

    public String getParkingDate() {
        if (parkingDate != null) {
            return parkingDate;
        }
        return "01.01.0001 00:00:00";
    }

    public String getEvacuationDate() {
        if (evacuationDate != null) {
            return evacuationDate;
        }
        return "01.01.0001 00:00:00";
    }

    public String getPoliceDepartment() {
        if(policeDepartment != null)
            return policeDepartment;
        return "";
    }

    public boolean isBlock() {
        return block;
    }

}
