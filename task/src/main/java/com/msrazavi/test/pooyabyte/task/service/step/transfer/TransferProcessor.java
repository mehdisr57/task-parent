package com.msrazavi.test.pooyabyte.task.service.step.transfer;

import com.msrazavi.test.pooyabyte.common.schema.entity.Account;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import com.msrazavi.test.pooyabyte.common.schema.entity.Voucher;
import com.msrazavi.test.pooyabyte.common.schema.entity.VoucherDetail;
import com.msrazavi.test.pooyabyte.task.model.TransferStepDto;
import com.msrazavi.test.pooyabyte.task.util.NextDateCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * create TransferStepDto base on request
 *
 * @author Mehdi Sadat Razavi
 */
@Service
public class TransferProcessor implements ItemProcessor<Request, TransferStepDto> {
    private static final Logger LOGGER = LogManager.getLogger(TransferProcessor.class);

    @Override
    public TransferStepDto process(Request request) {
        EntryMessage traceEntry = LOGGER.traceEntry("Request: {}", request);
        if (Objects.isNull(request)) return null;

        try {
            Optional<VoucherDetail> from = createVoucherDetail(request.getFrom(), request.getAmount(), BigDecimal.ZERO);
            Optional<VoucherDetail> to = createVoucherDetail(request.getTo(), BigDecimal.ZERO, request.getAmount());

            if (from.isPresent() && to.isPresent()) {
                TransferStepDto result = new TransferStepDto();
                result.setRequest(getRequest(request));
                result.setVoucher(getVoucher(request));
                result.getVoucherDetails().add(from.get());
                result.getVoucherDetails().add(to.get());
                return LOGGER.traceExit(traceEntry, result);
            }
        } catch (Exception e) {
            LOGGER.error("error on process - " + e.getMessage(), e);
        }

        return LOGGER.traceExit(traceEntry, null);
    }

    private Request getRequest(Request request) {
        EntryMessage traceEntry = LOGGER.traceEntry("Request: {}", request);
        NextDateCalculator calculator = new NextDateCalculator(request.getCourse(), request.getStartDate(), request.getLastDate());
        request.setNextDate(calculator.getNextDate());
        request.setLastDate(new Date());
        return LOGGER.traceExit(traceEntry, request);
    }

    private Voucher getVoucher(Request request) {
        EntryMessage traceEntry = LOGGER.traceEntry("Request: {}", request);
        Voucher result = new Voucher();
        result.setDate(new Date());
        result.setRequest(request);
        return LOGGER.traceExit(traceEntry, result);
    }

    private Optional<VoucherDetail> createVoucherDetail(Account account, BigDecimal debit, BigDecimal credit) {
        EntryMessage traceEntry = LOGGER.traceEntry("Account: {}, debit:{}, credit:{}", account, debit, credit);
        if (Objects.isNull(account) || Objects.isNull(debit) || Objects.isNull(credit)) {
            return Optional.empty();
        }
        VoucherDetail result = new VoucherDetail();
        result.setDebit(debit);
        result.setCredit(credit);
        result.setAccount(account);
        return LOGGER.traceExit(traceEntry, Optional.of(result));
    }
}
