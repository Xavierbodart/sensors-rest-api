package iba.proton.therapy.sensors.mapper;


import iba.proton.therapy.sensors.model.signal.SignalCO;
import iba.proton.therapy.sensors.model.signal.SignalEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = KeywordMapper.class)
public interface SignalMapper {

    SignalCO mapToCO(SignalEntity signalEntity);

    @InheritInverseConfiguration
    SignalEntity mapFromCO(SignalCO signalCO);

}