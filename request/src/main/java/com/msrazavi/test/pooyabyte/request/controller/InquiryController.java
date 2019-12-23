package com.msrazavi.test.pooyabyte.request.controller;

import com.msrazavi.test.pooyabyte.common.schema.dto.RequestDto;
import com.msrazavi.test.pooyabyte.common.schema.dto.ResponseDto;
import com.msrazavi.test.pooyabyte.common.schema.dto.VoucherDto;
import com.msrazavi.test.pooyabyte.common.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Mehdi Sadat Razavi
 */
@RestController
@RequestMapping("/rest/api/v1/inquiry")
public class InquiryController {
    private static final Logger LOGGER = LogManager.getLogger(InquiryController.class);

    private final RequestService requestService;

    public InquiryController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> get(@PathVariable("id") Long id) {
        EntryMessage traceEntry = LOGGER.traceEntry("id: {}", id);
        if (Objects.isNull(id)) {
            return LOGGER.traceExit(traceEntry, ResponseEntity.badRequest().build());
        }
        Optional<RequestDto> save = requestService.findWithVouchers(id);
        if (!save.isPresent()) {
            return LOGGER.traceExit(traceEntry, ResponseEntity.notFound().build());
        }

        String result;
        Set<VoucherDto> vouchers = save.get().getVouchers();
        if (Objects.isNull(vouchers) || vouchers.isEmpty()) {
            result = "request not started yet";
        } else {
            Optional<VoucherDto> lastVoucher = vouchers.stream().max(Comparator.comparing(VoucherDto::getDate));
            result = "request started in: " + vouchers.size() + " times, last in: " + lastVoucher.get().getDate();
        }
        return LOGGER.traceExit(traceEntry, ResponseEntity.ok(ResponseDto.ofSuccess(result)));
    }

}
