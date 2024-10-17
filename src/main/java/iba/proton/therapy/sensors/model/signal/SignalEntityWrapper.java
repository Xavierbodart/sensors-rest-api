package iba.proton.therapy.sensors.model.signal;

import iba.proton.therapy.sensors.model.base.ErrorMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignalEntityWrapper {

    private List<SignalEntity> signalEntities = new ArrayList<>();
    private List<ErrorMessage> errorMessages = new ArrayList<>();

}
