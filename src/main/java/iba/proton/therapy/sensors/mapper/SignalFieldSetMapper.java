package iba.proton.therapy.sensors.mapper;

import iba.proton.therapy.sensors.model.signal.DeadbandType;
import iba.proton.therapy.sensors.model.keyword.KeywordEntity;
import iba.proton.therapy.sensors.model.signal.SignalEntity;
import org.apache.logging.log4j.util.Strings;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static iba.proton.therapy.sensors.constants.SignalConstants.*;

public class SignalFieldSetMapper implements FieldSetMapper<SignalEntity> {

    @NonNull
    @Override
    public SignalEntity mapFieldSet(FieldSet fieldSet) {
        final SignalEntity signalEntity = new SignalEntity();
        final String keywords = fieldSet.readString(COLUMN_NAME_KEYWORDS).trim().replaceAll("[\\[\\]\\s+]",
                Strings.EMPTY);
        final String deadbandType = fieldSet.readString(COLUMN_NAME_DEADBAND_TYPE);
        final Set<Integer> keywordIds = Strings.isNotBlank(keywords) ?
                Pattern.compile(KEYWORDS_DELIMITER)
                        .splitAsStream(keywords)
                        .map(Integer::valueOf).collect(Collectors.toSet()) :
                new HashSet<>();
        signalEntity.setNodeId(fieldSet.readString(COLUMN_NAME_NODE_ID));
        signalEntity.setSamplingInterval(fieldSet.readInt(COLUMN_NAME_SAMPLING_INTERVAL_MS));
        signalEntity.setDeadbandValue(fieldSet.readInt(COLUMN_NAME_DEADBAND_VALUE, 0));
        signalEntity.setDeadbandType(Strings.isNotBlank(deadbandType) ? DeadbandType.valueOf(deadbandType) : null);
        signalEntity.setIsActive(fieldSet.readInt(COLUMN_NAME_ACTIVE) != 0);
        keywordIds.forEach(keywordId -> {
            KeywordEntity keywordEntity = new KeywordEntity();
            keywordEntity.setId(keywordId);
            signalEntity.getKeywords().add(keywordEntity);
        });
        return signalEntity;
    }
}
