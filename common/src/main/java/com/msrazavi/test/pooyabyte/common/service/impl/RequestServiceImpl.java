package com.msrazavi.test.pooyabyte.common.service.impl;

import com.msrazavi.test.pooyabyte.common.repository.RequestRepository;
import com.msrazavi.test.pooyabyte.common.schema.dto.RequestDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import com.msrazavi.test.pooyabyte.common.schema.mapper.BaseMapper;
import com.msrazavi.test.pooyabyte.common.schema.mapper.CycleAvoidingMappingContext;
import com.msrazavi.test.pooyabyte.common.schema.mapper.RequestMapper;
import com.msrazavi.test.pooyabyte.common.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
@Service
public class RequestServiceImpl
        extends BaseServiceImpl<Request, RequestDto, Long>
        implements RequestService {
    private static final Logger LOGGER = LogManager.getLogger(RequestServiceImpl.class);

    private RequestMapper mapper;
    private RequestRepository repository;

    @Autowired
    public void prepare(RequestMapper mapper,
                              RequestRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public RequestRepository getRepository() {
        return repository;
    }

    @Override
    public BaseMapper<Request, RequestDto> getMapper() {
        return mapper;
    }

    @Override
    public Optional<RequestDto> findWithVouchers(Long id) {
        EntryMessage traceEntry = LOGGER.traceEntry("id: {}", id);
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id in invalid");
        }
        Request request = repository.findWithVouchers(id);
        if (Objects.isNull(request)) {
            return Optional.empty();
        }

        return LOGGER.traceExit(traceEntry, Optional.of(mapper.entityToDto(request, new CycleAvoidingMappingContext())));
    }
}
