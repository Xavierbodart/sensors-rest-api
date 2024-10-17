package iba.proton.therapy.sensors.service;

import iba.proton.therapy.sensors.dao.KeywordRepository;
import iba.proton.therapy.sensors.dao.SignalRepository;
import iba.proton.therapy.sensors.mapper.KeywordMapper;
import iba.proton.therapy.sensors.mapper.SignalLoadMapper;
import iba.proton.therapy.sensors.mapper.SignalMapper;
import iba.proton.therapy.sensors.model.base.PagingCriteria;
import iba.proton.therapy.sensors.model.keyword.KeywordCO;
import iba.proton.therapy.sensors.model.signal.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.NoSuchElementException;

import static iba.proton.therapy.sensors.constants.SignalConstants.SIGNAL_SORT_FIELD_DEFAULT;

@Service
@Transactional
@AllArgsConstructor
public class SignalService {
    private static final Logger logger = LoggerFactory.getLogger(SignalService.class);

    private final SignalFileReaderService signalFileReaderService;

    private final SignalRepository signalRepository;
    private final KeywordRepository keywordRepository;

    private final SignalLoadMapper signalLoadMapper;
    private final SignalMapper signalMapper;
    private final KeywordMapper keywordMapper;


    public SignalCO getSignal(String nodeId) {
        final SignalEntity signalEntity =
                signalRepository.findById(nodeId).orElseThrow(() ->
                        new EntityNotFoundException("Entity not found with id: " + nodeId));
        return signalMapper.mapToCO(signalEntity);
    }

    public SignalLoadTriggerCO loadSignals() {
        final SignalEntityWrapper signalEntityWrapper = signalFileReaderService.readSignalFile();
        signalRepository.saveAll(signalEntityWrapper.getSignalEntities());
        return signalLoadMapper.mapToCO(signalEntityWrapper);
    }

    public List<SignalCO> searchSignals(SignalSearchCriteriaCO searchCriteria) {
        final PagingCriteria pagingCriteria = searchCriteria.getPagingCriteria();
        final String sortField = searchCriteria.getSortField() != null ? searchCriteria.getSortField().getField() :
                SIGNAL_SORT_FIELD_DEFAULT;
        final Sort.Direction sortDirection = searchCriteria.getSortDirection() != null ?
                searchCriteria.getSortDirection() : Sort.Direction.ASC;
        final Pageable pageable = PageRequest.of(pagingCriteria.getPage(), pagingCriteria.getPageSize(),
                Sort.by(sortDirection, sortField));
        final List<SignalEntity> signalEntities = signalRepository.findByCriteria(pageable,
                searchCriteria.getIsActive(), searchCriteria.getDeadbandType(), searchCriteria.getDeadbandValue(),
                searchCriteria.getSamplingInterval(), searchCriteria.getKeywordIds(),
                CollectionUtils.isEmpty(searchCriteria.getKeywordIds()) ? null : searchCriteria.getKeywordIds().size());
        return signalEntities.stream().map(signalMapper::mapToCO).toList();
    }

    public SignalCO updateSignal(String nodeId, SignalCO signalCO) {
        final SignalEntity signalEntityInput = signalMapper.mapFromCO(signalCO);
        final SignalEntity signalEntity =
                signalRepository.findById(nodeId).orElseThrow(() ->
                        new NoSuchElementException("no signal with node id " + nodeId));
        signalEntity.setDeadbandType(signalEntityInput.getDeadbandType());
        signalEntity.setSamplingInterval(signalEntityInput.getSamplingInterval());
        signalEntity.setDeadbandValue(signalEntityInput.getDeadbandValue());
        return signalMapper.mapToCO(signalRepository.save(signalEntity));
    }

    public List<KeywordCO> getKeywordsByNodeId(String nodeId) {
        return keywordRepository.findKeywordsByNodeId(nodeId).stream().map(keywordMapper::mapToCO).toList();
    }
}
