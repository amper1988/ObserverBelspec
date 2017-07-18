package retrofit.model;

import org.simpleframework.xml.Element;

public class CarDataFull {
    @Element(name = "manufacture", required = false)
    private String manufacture;
    @Element(name = "model", required = false)
    private String model;
    @Element(name = "color", required = false)
    private String color;
    @Element(name = "car_id", required = false)
    private String carId;
    @Element(name = "status", required = false)
    private String status;
    @Element(name = "police_department", required = false)
    private String policeDepartment;
    @Element(name = "police_officer", required = false)
    private String policeman;
    @Element(name = "protocol_number", required = false)
    private String protocolNumber;
    @Element(name = "clause", required = false)
    private String clause;
    @Element(name = "evacuated_bool", required = false)
    private boolean evacuatedBool;
    @Element(name = "evacuation_organization", required = false)
    private String evacuationOrganization;
    @Element(name = "wrecker", required = false)
    private String wrecker;
    @Element(name = "evacuation_date", required = false)
    private String evacuationDate;
    @Element(name = "image_1", required = false)
    private String image1;
    @Element(name = "image_2", required = false)
    private String image2;
    @Element(name = "image_3", required = false)
    private String image3;
    @Element(name = "image_4", required = false)
    private String image4;
    @Element(name = "evacuation_address", required = false)
    private String evacuationAddress;
    @Element(name = "storing_organization", required = false)
    private String storingOrganization;
    @Element(name = "watchman", required = false)
    private String whoTook;
    @Element(name = "parking_address", required = false)
    private String parkingAddress;
    @Element(name = "parking_date", required = false)
    private String parkingDate;
    @Element(name = "maps", required = false)
    private String maps;
    @Element(name = "block", required = false)
    private boolean block;
    @Element(name = "road_law_point", required = false)
    private String roadLawPoint;

    public String getManufacture() {
        return manufacture;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getCarId() {
        return carId;
    }

    public String getStatus() {
        return status;
    }

    public String getPoliceDepartment() {
        return policeDepartment;
    }

    public String getPoliceman() {
        return policeman;
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public String getClause() {
        return clause;
    }

    public boolean isEvacuatedBool() {
        return evacuatedBool;
    }

    public String getEvacuationOrganization() {
        return evacuationOrganization;
    }

    public String getWrecker() {
        return wrecker;
    }

    public String getEvacuationDate() {
        return evacuationDate;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }

    public String getEvacuationAddress() {
        return evacuationAddress;
    }

    public String getStoringOrganization() {
        return storingOrganization;
    }

    public String getWhoTook() {
        return whoTook;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public String getParkingDate() {
        return parkingDate;
    }
    public String getMaps() {
        return maps;
    }

    public boolean isBlock() {
        return block;
    }

    public String getRoadLawPoint() {
        return roadLawPoint;
    }
}
