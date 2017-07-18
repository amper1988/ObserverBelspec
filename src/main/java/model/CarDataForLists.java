package model;

import javafx.beans.property.*;

public class CarDataForLists {
    private IntegerProperty identifier;
    private StringProperty manufacture;
    private StringProperty model;
    private StringProperty carId;
    private StringProperty parkingDate;
    private StringProperty evacuationDate;
    private StringProperty protocolNumber;
    private StringProperty clause;
    private StringProperty policeDepartment;
    private StringProperty policeOfficer;
    private StringProperty roadLawPoint;
    private StringProperty parking;
    private StringProperty evacuationAddress;
    private BooleanProperty block;
    private int indexInTable = -1;

    public CarDataForLists() {
        identifier = new SimpleIntegerProperty();
        manufacture = new SimpleStringProperty();
        model = new SimpleStringProperty();
        carId = new SimpleStringProperty();
        parkingDate = new SimpleStringProperty();
        evacuationDate = new SimpleStringProperty();
        protocolNumber = new SimpleStringProperty();
        clause = new SimpleStringProperty();
        policeDepartment = new SimpleStringProperty();
        policeOfficer = new SimpleStringProperty();
        block = new SimpleBooleanProperty();
        roadLawPoint = new SimpleStringProperty();
        parking = new SimpleStringProperty();
        evacuationAddress = new SimpleStringProperty();
    }

    public String getEvacuationAddress() {
        return evacuationAddress.get();
    }

    public StringProperty evacuationAddressProperty() {
        return evacuationAddress;
    }

    public void setEvacuationAddress(String evacuationAddress) {
        this.evacuationAddress.set(evacuationAddress);
    }

    public String getEvacuationDate() {
        return evacuationDate.get();
    }

    public StringProperty evacuationDateProperty() {
        return evacuationDate;
    }

    public void setEvacuationDate(String evacuationDate) {
        this.evacuationDate.set(evacuationDate);
    }

    public String getPoliceOfficer() {
        return policeOfficer.get();
    }

    public StringProperty policeOfficerProperty() {
        if(policeOfficer != null)
            return policeOfficer;
        return new SimpleStringProperty();
    }

    public void setPoliceOfficer(String policeOfficer) {
        this.policeOfficer.set(policeOfficer);
    }

    public String getRoadLawPoint() {
        return roadLawPoint.get();
    }

    public StringProperty roadLawPointProperty() {
        return roadLawPoint;
    }

    public void setRoadLawPoint(String roadLawPoint) {
        this.roadLawPoint.set(roadLawPoint);
    }

    public int getIdentifier() {
        return identifier.get();
    }

    public IntegerProperty identifierProperty() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier.set(identifier);
    }

    public String getManufacture() {
        return manufacture.get();
    }

    public StringProperty manufactureProperty() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture.set(manufacture);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getCarId() {
        return carId.get();
    }

    public StringProperty carIdProperty() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId.set(carId);
    }

    public String getParkingDate() {
        return parkingDate.get();
    }

    public StringProperty parkingDateProperty() {
        return parkingDate;
    }

    public void setParkingDate(String parkingDate) {
        this.parkingDate.set(parkingDate);
    }

    public String getProtocolNumber() {
        return protocolNumber.get();
    }

    public StringProperty protocolNumberProperty() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber.set(protocolNumber);
    }

    public String getClause() {
        return clause.get();
    }

    public StringProperty clauseProperty() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause.set(clause);
    }

    public String getPoliceDepartment() {
        return policeDepartment.get();
    }

    public StringProperty policeDepartmentProperty() {
        return policeDepartment;
    }

    public void setPoliceDepartment(String policeDepartment) {
        this.policeDepartment.set(policeDepartment);
    }

    public boolean isBlock() {
        return block.get();
    }

    public BooleanProperty blockProperty() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block.set(block);
    }

    public int getIndexInTable() {
        return indexInTable;
    }

    public void setIndexInTable(int indexInTable) {
        this.indexInTable = indexInTable;
    }

    public String getParking() {
        return parking.get();
    }

    public StringProperty parkingProperty() {
        return parking;
    }

    public void setParking(String parking) {

        this.parking.set(parking);
    }
}
