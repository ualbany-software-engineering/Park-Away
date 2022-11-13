package com.parkway.model;


import java.io.Serializable;


public class DistanceMatrixResponse implements Serializable {
    private String[] destinationAddresses;
    private String[] originAddresses;
    private Row[] rows;
    private String status;

    public String[] getDestinationAddresses() { return destinationAddresses; }
    public void setDestinationAddresses(String[] value) { this.destinationAddresses = value; }

    public String[] getOriginAddresses() { return originAddresses; }
    public void setOriginAddresses(String[] value) { this.originAddresses = value; }

    public Row[] getRows() { return rows; }
    public void setRows(Row[] value) { this.rows = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }
}
