package com.msrazavi.test.pooyabyte.task.service;

import com.msrazavi.test.pooyabyte.common.repository.AccountRepository;
import com.msrazavi.test.pooyabyte.common.schema.dto.RequestDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Account;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import com.msrazavi.test.pooyabyte.common.schema.mapper.CycleAvoidingMappingContext;
import com.msrazavi.test.pooyabyte.common.service.impl.RequestServiceImpl;
import com.msrazavi.test.pooyabyte.task.util.NextDateCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
@Service
public class TaskRequestServiceImpl extends RequestServiceImpl {
    private static final Logger LOGGER = LogManager.getLogger(TaskRequestServiceImpl.class);

    private AccountRepository accountRepository;

    @Autowired
    public void prepare(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<RequestDto> save(RequestDto request) {
        try {
            EntryMessage traceEntry = LOGGER.traceEntry("RequestDto: {}", request);
            if (Objects.nonNull(request) && Objects.isNull(request.getNextDate())) {
                NextDateCalculator calculator = new NextDateCalculator(request.getCourse(), request.getStartDate(), request.getLastDate());
                request.setNextDate(calculator.getNextDate());
            }
            Request entity = getMapper().dtoToEntity(request, new CycleAvoidingMappingContext());
            Optional<Account> fromAccount = accountRepository.findById(request.getFrom().getId());
            Optional<Account> toAccount = accountRepository.findById(request.getTo().getId());
            if (!fromAccount.isPresent() || !toAccount.isPresent()) {
                throw new IllegalArgumentException("account is invalid");
            }
            entity.setFrom(fromAccount.get());
            entity.setTo(toAccount.get());
            Request save = getRepository().save(entity);
            RequestDto result = getMapper().entityToDto(save, new CycleAvoidingMappingContext());

            return LOGGER.traceExit(traceEntry, Optional.ofNullable(result));
        } catch (Exception e) {
            LOGGER.error("error in save - " + e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<RequestDto> findWithVouchers(Long id) {
        EntryMessage traceEntry = LOGGER.traceEntry("id: {}", id);
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id in invalid");
        }
        Request request = getRepository().findWithVouchers(id);
        if (Objects.isNull(request)) {
            return Optional.empty();
        }

        return LOGGER.traceExit(traceEntry, Optional.of(getMapper().entityToDto(request, new CycleAvoidingMappingContext())));
    }
}
