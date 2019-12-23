package com.msrazavi.test.pooyabyte.common.service.impl;

import com.msrazavi.test.pooyabyte.common.schema.dto.BaseDto;
import com.msrazavi.test.pooyabyte.common.schema.entity.BaseEntity;
import com.msrazavi.test.pooyabyte.common.schema.mapper.BaseMapper;
import com.msrazavi.test.pooyabyte.common.schema.mapper.CycleAvoidingMappingContext;
import com.msrazavi.test.pooyabyte.common.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
public abstract class BaseServiceImpl<ENTITY extends BaseEntity, DTO extends BaseDto, ID extends Serializable>
        implements BaseService<ENTITY, DTO, ID> {

    private static final Logger LOG = LogManager.getLogger(BaseServiceImpl.class);

    @Override
    public Optional<DTO> findById(ID id) {
        Optional<ENTITY> optionalENTITY = getRepository().findById(id);
        return optionalENTITY.map(entity -> getMapper().entityToDto(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<DTO> save(DTO model) {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - model: {}", model);

        DTO oModel = Optional.of(model).orElseThrow(() -> new IllegalArgumentException("Invalid params"));

        try {
            LOG.trace("convert modelToEntity started");
            ENTITY entity = getMapper().dtoToEntity(oModel, new CycleAvoidingMappingContext());
            ENTITY save = getRepository().save(entity);

            LOG.trace("convert entityToModel started");
            DTO result = getMapper().entityToDto(save, new CycleAvoidingMappingContext());

            return LOG.traceExit(traceEntry, Optional.ofNullable(result));
        } catch (Exception e) {
            LOG.error("error in save - " + e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<DTO> findAll() {
        EntryMessage traceEntry = LOG.traceEntry();
        List<DTO> result = new ArrayList<>();
        Iterable<ENTITY> all = getRepository().findAll();
        for (ENTITY entity : all) {
            result.add(getMapper().entityToDto(entity, new CycleAvoidingMappingContext()));
        }
        return LOG.traceExit(traceEntry, result);
    }

    abstract CrudRepository<ENTITY, ID> getRepository();

    abstract BaseMapper<ENTITY, DTO> getMapper();
}
