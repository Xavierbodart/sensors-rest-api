package iba.proton.therapy.sensors;

import iba.proton.therapy.sensors.dao.SignalRepository;
import iba.proton.therapy.sensors.model.base.ResultObject;
import iba.proton.therapy.sensors.model.keyword.KeywordEntity;
import iba.proton.therapy.sensors.model.signal.DeadbandType;
import iba.proton.therapy.sensors.model.signal.SignalCO;
import iba.proton.therapy.sensors.model.signal.SignalEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = SensorsRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignalTests {
    public static final String RESOURCE_NAME = "signals";

    private static final String TEST_NODE_ID = "test_node_id";
    private static final int TEST_SAMPLING_INTERVAL = 500;
    private static final DeadbandType TEST_DEADBAND_TYPE = DeadbandType.ABSOLUTE;
    private static final int TEST_DEADBAND_VALUE = 88;
    private static final int TEST_UPDATED_DEADBAND_VALUE = 55;
    private static final boolean TEST_IS_ACTIVE = true;
    private static final int TEST_KEYWORD_ID_1 = 1;
    private static final int TEST_KEYWORD_ID_2 = 2;


    private static String getRootUrl() {
        return "http://localhost:8080";
    }

    private static String getResourceUrl() {
        return getRootUrl() + "/" + RESOURCE_NAME;
    }

    private static final ParameterizedTypeReference<ResultObject<SignalCO>> responseType =
            new ParameterizedTypeReference<>() {
            };
    private static final ParameterizedTypeReference<ResultObject<List<SignalCO>>> listResponseType =
            new ParameterizedTypeReference<>() {
            };

    private final TestRestTemplate restTemplate;
    private final SignalRepository signalRepository;

    @Autowired
    public SignalTests(TestRestTemplate restTemplate, SignalRepository signalRepository) {
        this.restTemplate = restTemplate;
        this.signalRepository = signalRepository;
    }

    @Test
    @Order(1)
    public void testCreateSignal() {
        final SignalEntity signalEntity = getSignalEntityTestObject();
        SignalEntity saveSignalEntity = signalRepository.save(signalEntity);
        Assertions.assertNotNull(saveSignalEntity);
        Assertions.assertNotNull(saveSignalEntity.getCreationDate());
        Assertions.assertNotNull(saveSignalEntity.getModificationDate());
        Assertions.assertEquals(TEST_NODE_ID, saveSignalEntity.getNodeId());
        Assertions.assertEquals(TEST_DEADBAND_TYPE, saveSignalEntity.getDeadbandType());
        Assertions.assertEquals(TEST_SAMPLING_INTERVAL, saveSignalEntity.getSamplingInterval());
    }

    @Test
    @Order(2)
    public void testGetSignalById() {
        ResponseEntity<ResultObject<SignalCO>> getResponse =
                restTemplate.exchange(getResourceUrl() + "/" + TEST_NODE_ID, HttpMethod.GET, null, responseType);
        Assertions.assertNotNull(getResponse); Assertions.assertNotNull(getResponse.getBody());
        Assertions.assertNotNull(getResponse.getBody().getResult());
        Assertions.assertNotNull(getResponse.getBody().getResult().getNodeId());
        Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }

    @Test
    @Order(3)
    public void testSearchSignal() {
        final String criteriaPath = """
                ?pagingCriteria.page=0&pagingCriteria.pageSize=5&sortField=CREATION_DATE&sortDirection=DESC\
                &isActive=%s&deadbandType=%s&deadbandValue=%s&samplingInterval=%s&keywordIds=%s&keywordIds=%s\
                """.formatted(TEST_IS_ACTIVE, TEST_DEADBAND_TYPE, TEST_DEADBAND_VALUE, TEST_SAMPLING_INTERVAL,
                TEST_KEYWORD_ID_1, TEST_KEYWORD_ID_2);
        final ResponseEntity<ResultObject<List<SignalCO>>> getResponse =
                restTemplate.exchange(getResourceUrl() + criteriaPath, HttpMethod.GET, null,
                        listResponseType);
        Assertions.assertNotNull(getResponse);
        Assertions.assertNotNull(getResponse.getBody());
        Assertions.assertNotNull(getResponse.getBody().getResult());
        Assertions.assertFalse(CollectionUtils.isEmpty(getResponse.getBody().getResult()));

        List<SignalCO> signalListResult = getResponse.getBody().getResult();
        SignalCO createdSignal = signalListResult.stream().filter(signal -> TEST_NODE_ID.equals(signal.
                getNodeId())).findFirst().orElse(null);

        Assertions.assertNotNull(createdSignal);
    }

    @Test
    @Order(4)
    public void testUpdateSignal() {
        ResponseEntity<ResultObject<SignalCO>> getResponse =
                restTemplate.exchange(getResourceUrl() + "/" + TEST_NODE_ID, HttpMethod.GET, null, responseType);
        Assertions.assertNotNull(getResponse); Assertions.assertNotNull(getResponse.getBody());
        Assertions.assertNotNull(getResponse.getBody().getResult());
        Assertions.assertNotNull(getResponse.getBody().getResult().getNodeId());

        SignalCO signalCO = getResponse.getBody().getResult();
        Assertions.assertNotNull(signalCO);
        Assertions.assertNotNull(signalCO.getNodeId());

        signalCO.setDeadbandValue(TEST_UPDATED_DEADBAND_VALUE);

        final HttpEntity<SignalCO> request = new HttpEntity<>(signalCO);
        final ResponseEntity<ResultObject<SignalCO>> putResponse =
                restTemplate.exchange(getResourceUrl() + "/" + TEST_NODE_ID, HttpMethod.PUT, request, responseType);

        Assertions.assertNotNull(putResponse);
        Assertions.assertNotNull(putResponse.getBody());
        Assertions.assertNotNull(putResponse.getBody().getResult());
        Assertions.assertNotNull(putResponse.getBody().getResult().getNodeId());
        Assertions.assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        Assertions.assertTrue(putResponse.getBody().getSuccess());

        SignalCO updatedSignal = putResponse.getBody().getResult();
        Assertions.assertEquals(TEST_UPDATED_DEADBAND_VALUE, updatedSignal.getDeadbandValue());
    }

    @Test
    @Order(5)
    public void deleteSignal() {
        signalRepository.findById(TEST_NODE_ID).ifPresent(signalRepository::delete);
    }

    private static SignalEntity getSignalEntityTestObject() {
        final SignalEntity signalEntity = new SignalEntity();
        signalEntity.setNodeId(TEST_NODE_ID);
        signalEntity.setSamplingInterval(TEST_SAMPLING_INTERVAL);
        signalEntity.setDeadbandType(TEST_DEADBAND_TYPE);
        signalEntity.setDeadbandValue(TEST_DEADBAND_VALUE);
        signalEntity.setIsActive(TEST_IS_ACTIVE);
        List<Integer> keywordIds = List.of(TEST_KEYWORD_ID_1, TEST_KEYWORD_ID_2);
        keywordIds.forEach(keywordId -> {
            KeywordEntity keywordEntity = new KeywordEntity();
            keywordEntity.setId(keywordId);
            signalEntity.getKeywords().add(keywordEntity);
        });
        return signalEntity;
    }

}
