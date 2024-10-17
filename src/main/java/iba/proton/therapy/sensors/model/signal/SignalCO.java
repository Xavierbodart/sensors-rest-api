package iba.proton.therapy.sensors.model.signal;


import iba.proton.therapy.sensors.model.keyword.KeywordCO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class SignalCO {

    @NotNull
    private String nodeId;
    private Integer samplingInterval;
    private DeadbandType deadbandType;
    private Integer deadbandValue;
    @NotNull
    private Boolean isActive;
    private Set<KeywordCO> keywords = new HashSet<>();
    private Date creationDate;
    private Date modificationDate;
}
