package com.msrazavi.test.pooyabyte.task.service.step.transfer;

import com.msrazavi.test.pooyabyte.task.model.TransferStepDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * insert voucher, two voucherDetails and update request
 *
 * @author Mehdi Sadat Razavi
 */
@Service
public class TransferWriter implements ItemWriter<TransferStepDto> {
    private static final Logger LOGGER = LogManager.getLogger(TransferWriter.class);

    private final TransferWriterHelper writerHelper;

    public TransferWriter(TransferWriterHelper writerHelper) {
        this.writerHelper = writerHelper;
    }

    @AfterStep
    public void init() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        LOGGER.info("Step Transfer finished");
        LOGGER.traceExit(traceEntry);
    }

    @Override
    public void write(List<? extends TransferStepDto> items) {
        EntryMessage traceEntry = LOGGER.traceEntry("TransferStepDto: {}", items);
        for (TransferStepDto dto : items) {
            try {
                writerHelper.write(dto);
            } catch (Exception e) {
                LOGGER.error("error on write TransferStepDto - request: " + dto.getRequest() + "- " + e.getMessage(), e);
            }
        }
        LOGGER.traceExit(traceEntry);
    }
}
