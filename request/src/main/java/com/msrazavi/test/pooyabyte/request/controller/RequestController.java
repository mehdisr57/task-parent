package com.msrazavi.test.pooyabyte.request.controller;

import com.msrazavi.test.pooyabyte.common.schema.dto.RequestDto;
import com.msrazavi.test.pooyabyte.common.schema.dto.ResponseDto;
import com.msrazavi.test.pooyabyte.common.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Mehdi Sadat Razavi
 */
@RestController
@RequestMapping("/rest/api/v1/request")
public class RequestController {
    private static final Logger LOGGER = LogManager.getLogger(RequestController.class);

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Long>> add(@RequestBody @Valid RequestDto request, BindingResult bindingResult) {
        EntryMessage traceEntry = LOGGER.traceEntry("RequestDto: {}, BindingResult: {}", request, bindingResult);
        if (bindingResult.hasErrors()) {
            LOGGER.warn("error in input parameter - " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return LOGGER.traceExit(traceEntry, ResponseEntity.badRequest().build());
        }
        request.setId(null);
        Optional<RequestDto> save = requestService.save(request);
        if (save.isPresent()) {
            return LOGGER.traceExit(traceEntry, ResponseEntity.ok(ResponseDto.ofSuccess(save.get().getId())));
        }
        return LOGGER.traceExit(traceEntry, ResponseEntity.badRequest().build());
    }

}
