package com.web.demo.services;

import com.web.demo.dtos.DepartmentsDto;
import com.web.demo.dtos.HrRecordDto;
import com.web.demo.dtos.HrRecordMinimalDto;
import com.web.demo.models.emp.HrRecords;
import com.web.demo.projection.HrDetails;
import com.web.demo.records.HrDetailsRecord;
import com.web.demo.repos.emp.HrRecordRepository;
import jakarta.persistence.QueryHint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;

@Service
public class HrRecordServiceImpl implements HrRecordService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final HrRecordRepository hrRecordRepository;

    public HrRecordServiceImpl(HrRecordRepository hrRecordRepository) {
        this.hrRecordRepository = hrRecordRepository;
    }

    private final ConcurrentHashMap<String, List<HrDetailsRecord>> cache = new ConcurrentHashMap<>();
    private final List<HrDetailsRecord> hrDetailsRecordsGlobal = new ArrayList<>();

    @Override
    public List<HrRecordDto> findTop10By() {
        LOGGER.info("findTop10By Entry");
        List<HrRecords>  hrRecordsList = hrRecordRepository.findTop10By();
        if (hrRecordsList.isEmpty()) {
            LOGGER.warn("findTop10By() No HR records found in the database");
            return List.of();
        }
        return toDtoList(hrRecordsList);
    }

    @Override
    public List<HrRecordDto> findTop10WithDepartments() {
        Pageable pageable = PageRequest.of(0, 10);
        List<HrRecords> hrRecordsList = hrRecordRepository.findTop10WithDepartments(pageable);
        if (hrRecordsList.isEmpty()) {
            LOGGER.warn("findTop10WithDepartments() No HR records found in the database");
            return List.of();
        }
        return toDtoList(hrRecordsList);
    }

    @Override
    public List<HrRecordDto> findTop1000WithDepartments() {
        Pageable pageable = PageRequest.of(0, 1000);
        List<HrRecords> hrRecordsList = hrRecordRepository.findTop1000WithDepartments(pageable);
        if (hrRecordsList.isEmpty()) {
            LOGGER.warn("findTop1000WithDepartments() No HR records found in the database");
            return List.of();
        }
        return toDtoList(hrRecordsList);
    }

    @Override
    public List<HrRecordMinimalDto> findTop1000WithMinimal() {
        Pageable pageable = PageRequest.of(0, 2000);
        List<HrRecordMinimalDto> hrRecordsList = hrRecordRepository.findTop1000WithMinimal(pageable);
        if (hrRecordsList.isEmpty()) {
            LOGGER.warn("findTop1000WithMinimal() No HR records found in the database");
            return List.of();
        }
        return hrRecordsList;
    }

    @Override
    public List<HrDetails> getHrDetails() {
        return hrRecordRepository.findAllBy();
    }

    @Override
    public List<HrDetailsRecord> getHrDetailsObject() {
        return hrRecordRepository.getHrDetailsObject();
    }

    @Override
    //@CachePut(value = "hrDetailsObjectLimit1000")
    public List<HrDetailsRecord> getHrDetailsObjectLimit1000() {
        LOGGER.info("Running in thread: {}", Thread.currentThread().getName());
        List<HrDetailsRecord> hrDetailsRecords = hrRecordRepository.getHrDetailsObjectLimit1000();
        cache.put("hrDetailsRecords", hrDetailsRecords);
        hrDetailsRecordsGlobal.addAll(hrDetailsRecords);
        return hrDetailsRecords;
    }

    @Override
    @Transactional(readOnly = true)
    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
    public void streamUsersToEmitter(ResponseBodyEmitter emitter) {
        try (Stream<HrRecords> stream = hrRecordRepository.streamAllUsers()) {
            stream.map(this::toDto)
                    .forEach(dto -> {
                        try {
                            emitter.send(dto);
                            Thread.sleep(30_000);
                        } catch (IOException | InterruptedException e) {
                            emitter.completeWithError(e);
                        }
                    });
            emitter.complete();
        } catch (Exception ex) {
            emitter.completeWithError(ex);
        }
    }

    @Override
    public List<HrRecordDto> getAllHrRecord() {
        LOGGER.info("getAllHrRecord");
        List<HrRecords> hrRecordsList = hrRecordRepository.findAll();
        if (hrRecordsList.isEmpty()) {
            LOGGER.warn("getAllHrRecord() No HR records found in the database");
            return List.of();
        }
        /*try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }*/
        return toDtoList(hrRecordsList);
    }

    public List<HrRecordDto> toDtoList(List<HrRecords> hrRecordsList) {
        return Optional.of(hrRecordsList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::toDto).toList();
    }

    public HrRecordDto toDto(HrRecords entity) {
        return new HrRecordDto(
                entity.getId(),
                entity.getEmpId(),
                entity.getNamePrefix(),
                entity.getFirstName(),
                entity.getMiddleInitial(),
                entity.getLastName(),
                entity.getGender(),
                entity.getEmail(),
                entity.getFatherName(),
                entity.getMotherName(),
                entity.getMotherMaidenName(),
                entity.getDateOfBirth(),
                entity.getTimeOfBirth(),
                entity.getAgeInYrs(),
                entity.getWeightInKgs(),
                entity.getDateOfJoining(),
                entity.getQuarterOfJoining(),
                entity.getHalfOfJoining(),
                entity.getYearOfJoining(),
                entity.getMonthOfJoining(),
                entity.getMonthNameOfJoining(),
                entity.getShortMonth(),
                entity.getDayOfJoining(),
                entity.getDowOfJoining(),
                entity.getShortDow(),
                entity.getAgeInCompanyYears(),
                entity.getSalary(),
                entity.getLastHike(),
                entity.getSsn(),
                entity.getPhoneNo(),
                entity.getPlaceName(),
                entity.getCounty(),
                entity.getCity(),
                entity.getState(),
                entity.getZip(),
                entity.getRegion(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getDepartment().getDepartmentId(),
                entity.getDepartment().getDepartmentName()
        );
    }

}
