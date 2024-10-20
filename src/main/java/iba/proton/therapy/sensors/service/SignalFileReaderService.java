package iba.proton.therapy.sensors.service;

import iba.proton.therapy.sensors.mapper.SignalFieldSetMapper;
import iba.proton.therapy.sensors.model.base.ErrorMessage;
import iba.proton.therapy.sensors.model.signal.SignalEntity;
import iba.proton.therapy.sensors.model.signal.SignalEntityWrapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import static iba.proton.therapy.sensors.constants.SignalConstants.*;

@Service
@Transactional
public class SignalFileReaderService {

    private static final Logger logger = LoggerFactory.getLogger(SignalFileReaderService.class);

    public static final int FIRST_LINE_TO_READ = 2;

    @Value("${signal.file.path}")
    private String signalFilePath;

    private final FlatFileItemReader<SignalEntity> itemReader;

    public SignalFileReaderService() {
        this.itemReader = new FlatFileItemReader<>();
    }

    public SignalEntityWrapper readSignalFile() {
        int lineNumber = FIRST_LINE_TO_READ;
        initializeItemReader();
        SignalEntityWrapper signalEntityWrapper = new SignalEntityWrapper();
        SignalEntity currentSignal = new SignalEntity();

        try {
            do {
                try {
                    currentSignal = itemReader.read();
                    if (currentSignal != null) {
                        signalEntityWrapper.getSignalEntities().add(currentSignal);
                    }
                } catch (Exception e) {
                    final String errorDescription = String.format("Ignored error on line %s in file %s", lineNumber,
                            signalFilePath);
                    final ErrorMessage errorMessage = new ErrorMessage(errorDescription, e.getMessage());
                    signalEntityWrapper.getErrorMessages().add(errorMessage);
                    logger.warn(errorDescription, e);
                }
                lineNumber++;

            }
            while (currentSignal != null);
        } finally {
            itemReader.close();
        }
        return signalEntityWrapper;
    }

    private void initializeItemReader() {
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        final DefaultLineMapper<SignalEntity> lineMapper = new DefaultLineMapper<>();
        tokenizer.setNames(COLUMN_NAME_NODE_ID, COLUMN_NAME_SAMPLING_INTERVAL_MS, COLUMN_NAME_DEADBAND_VALUE,
                COLUMN_NAME_DEADBAND_TYPE, COLUMN_NAME_ACTIVE, COLUMN_NAME_KEYWORDS);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new SignalFieldSetMapper());
        itemReader.setResource(new FileSystemResource(signalFilePath));
        itemReader.setLineMapper(lineMapper);
        itemReader.setLinesToSkip(1);
        itemReader.open(new ExecutionContext());
    }

}
