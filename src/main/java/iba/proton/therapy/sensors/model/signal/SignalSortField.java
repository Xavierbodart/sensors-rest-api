package iba.proton.therapy.sensors.model.signal;

public enum SignalSortField {
    NODE_ID("nodeId"),
    CREATION_DATE("creationDate"),
    MODIFICATION_DATE("modificationDate"),
    SAMPLING_INTERVAL("samplingInterval"),
    IS_ACTIVE("isActive"),
    DEADBAND_TYPE("deadbandType"),
    DEADBAND_VALUE("deadbandValue");

    private final String field;

    SignalSortField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
