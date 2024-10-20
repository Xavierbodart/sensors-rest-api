package iba.proton.therapy.sensors.model.signal;

import iba.proton.therapy.sensors.model.base.PagingCriteria;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

@Data
public class SignalSearchCriteriaCO {

    private PagingCriteria pagingCriteria;

    private SignalSortField sortField;
    private Sort.Direction sortDirection;

    private Boolean isActive;
    private DeadbandType deadbandType;
    @PositiveOrZero(message = "Deadband value must be positive")
    private Integer deadbandValue;
    @PositiveOrZero(message = "Sampling interval must be positive")
    private Integer samplingInterval;
    private Set<@Positive(message = "Keyword ID must be positive") Integer> keywordIds;

}
