package com.msrazavi.test.pooyabyte.task.service.step.transfer;

import com.msrazavi.test.pooyabyte.common.repository.RequestRepository;
import com.msrazavi.test.pooyabyte.common.repository.VoucherDetailRepository;
import com.msrazavi.test.pooyabyte.common.repository.VoucherRepository;
import com.msrazavi.test.pooyabyte.common.schema.entity.Request;
import com.msrazavi.test.pooyabyte.common.schema.entity.Voucher;
import com.msrazavi.test.pooyabyte.common.schema.entity.VoucherDetail;
import com.msrazavi.test.pooyabyte.task.model.TransferStepDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * this helper used for transactional insertion
 *
 * @author Mehdi Sadat Razavi
 */
@Service
public class TransferWriterHelper {

    private static final Logger LOGGER = LogManager.getLogger(TransferWriterHelper.class);

    private final VoucherRepository voucherRepository;
    private final VoucherDetailRepository voucherDetailRepository;
    private final RequestRepository requestRepository;

    public TransferWriterHelper(VoucherRepository voucherRepository,
                                VoucherDetailRepository voucherDetailRepository,
                                RequestRepository requestRepository) {
        this.voucherRepository = voucherRepository;
        this.voucherDetailRepository = voucherDetailRepository;
        this.requestRepository = requestRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void write(TransferStepDto dto) {
        EntryMessage traceEntry = LOGGER.traceEntry("TransferStepDto: {}", dto);

        Request request = requestRepository.save(dto.getRequest());
        dto.getVoucher().setRequest(request);
        Voucher savedVoucher = voucherRepository.save(dto.getVoucher());
        for (VoucherDetail voucherDetail : dto.getVoucherDetails()) {
            voucherDetail.setVoucher(savedVoucher);
            voucherDetailRepository.save(voucherDetail);
        }
        LOGGER.traceExit(traceEntry);
    }
}
