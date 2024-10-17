package iba.proton.therapy.sensors.model.signal;

import java.util.Set;

public record SignalRecord(
        String nodeId,
        int samplingInterval,
        String deadbandType,
        int deadbandValue,
        boolean isActive,
        Set<Integer> keywordIds
) {
}
