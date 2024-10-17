package iba.proton.therapy.sensors.controller;

import iba.proton.therapy.sensors.model.base.ResultObject;
import iba.proton.therapy.sensors.model.keyword.KeywordCO;
import iba.proton.therapy.sensors.model.signal.SignalCO;
import iba.proton.therapy.sensors.model.signal.SignalLoadTriggerCO;
import iba.proton.therapy.sensors.model.signal.SignalSearchCriteriaCO;
import iba.proton.therapy.sensors.service.SignalService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/signals")
public class SignalsController extends AbstractController {

    private final SignalService signalService;

    @Autowired
    public SignalsController(final SignalService signalService) {
        super("Signals management");
        this.signalService = signalService;
    }

    @GetMapping("/{nodeId}")
    public ResultObject<SignalCO> getSignal(@PathVariable("nodeId") String nodeId) {
        return mapToResultObject(signalService.getSignal(nodeId));
    }

    @PutMapping("/{nodeId}")
    public ResultObject<SignalCO> updateSignal(@PathVariable("nodeId") String nodeId,
                                               @Valid @RequestBody SignalCO signalCO) {
        return mapToResultObject(signalService.updateSignal(nodeId, signalCO));
    }

    @GetMapping
    public ResultObject<List<SignalCO>> searchSignals(@Valid @ParameterObject @ModelAttribute SignalSearchCriteriaCO searchCriteria) {
        List<SignalCO> results = signalService.searchSignals(searchCriteria);
        return mapToResultObject(results);
    }

    @PostMapping(path = "/load-triggers")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultObject<SignalLoadTriggerCO> loadSignals() {
        return mapToResultObject(signalService.loadSignals());
    }

    @GetMapping("/{nodeId}/keywords")
    public ResultObject<List<KeywordCO>> getKeywordsByNodeId(@PathVariable("nodeId") String nodeId) {
        return mapToResultObject(signalService.getKeywordsByNodeId(nodeId));
    }

}
