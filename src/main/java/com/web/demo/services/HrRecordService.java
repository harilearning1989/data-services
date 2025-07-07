package com.web.demo.services;

import com.web.demo.dtos.HrRecordDto;
import com.web.demo.projection.HrDetails;
import com.web.demo.records.HrDetailsRecord;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

public interface HrRecordService {
    List<HrRecordDto> getAllHrRecord();

    List<HrRecordDto> findTop10By();

    List<HrDetails> getHrDetails();

    List<HrDetailsRecord> getHrDetailsObject();

    List<HrDetailsRecord> getHrDetailsObjectLimit1000();

    void streamUsersToEmitter(ResponseBodyEmitter emitter);
}
