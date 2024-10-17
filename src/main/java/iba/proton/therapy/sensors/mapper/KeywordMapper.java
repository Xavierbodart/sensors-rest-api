package iba.proton.therapy.sensors.mapper;


import iba.proton.therapy.sensors.model.keyword.KeywordCO;
import iba.proton.therapy.sensors.model.keyword.KeywordEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KeywordMapper {

    KeywordCO mapToCO(KeywordEntity keywordEntity);

    @InheritInverseConfiguration
    KeywordEntity mapFromCO(KeywordCO keywordCO);
}