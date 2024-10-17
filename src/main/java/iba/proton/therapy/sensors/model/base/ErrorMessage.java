package iba.proton.therapy.sensors.model.base;

import lombok.Data;

@Data
public class ErrorMessage {

    private String errorMessage;
    private String errorCode;

    public ErrorMessage(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
