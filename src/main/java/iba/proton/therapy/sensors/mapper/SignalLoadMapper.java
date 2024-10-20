package iba.proton.therapy.sensors.mapper;


import iba.proton.therapy.sensors.model.signal.SignalEntity;
import iba.proton.therapy.sensors.model.signal.SignalEntityWrapper;
import iba.proton.therapy.sensors.model.signal.SignalLoadTriggerCO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SignalLoadMapper {

    @Mapping(source = "signalLoadMapper.signalEntities", target = "successRowsLoadCount",
            qualifiedByName = "getSuccessRowsLoadCount")
    public abstract SignalLoadTriggerCO mapToCO(SignalEntityWrapper signalLoadMapper);

    @Named("getSuccessRowsLoadCount")
    int getSuccessRowsLoadCount(List<SignalEntity> signalEntities) {
        return CollectionUtils.isEmpty(signalEntities) ? 0 : signalEntities.size();
    }

}