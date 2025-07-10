package com.web.demo.controls;

import com.web.demo.dtos.HrRecordDto;
import com.web.demo.projection.HrDetails;
import com.web.demo.records.HrDetailsRecord;
import com.web.demo.services.HrRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("hr")
public class HrRecordRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final HrRecordService hrRecordService;

    public HrRecordRestController(HrRecordService hrRecordService) {
        this.hrRecordService = hrRecordService;
    }

    @Operation(
            summary = "Get top 10 HR records",
            description = "Returns the top 10 HR records from the database. Optionally filters by department if the 'department' query parameter is provided."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of top 10 HR records"),
            @ApiResponse(responseCode = "204", description = "No HR records found")
    })
    @GetMapping("top10")
    public ResponseEntity<List<HrRecordDto>> findTop10By(@RequestParam(value = "department", required = false) String department) {
        List<HrRecordDto> hrRecordDtoList = hrRecordService.findTop10By();
        if (hrRecordDtoList.isEmpty()) {
            LOGGER.warn("findTop10By() No HR records found");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("findTop10By() Returning {} HR records", hrRecordDtoList.size());
        return ResponseEntity.ok(hrRecordDtoList);
    }

    @Operation(summary = "Get HR details (ID and City)", description = "Returns a list of HR details including ID and City.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of HR details"),
            @ApiResponse(responseCode = "204", description = "No HR records found")
    })
    @GetMapping("idAndCity")
    public ResponseEntity<List<HrDetails>> getHrDetails() {
        List<HrDetails> hrRecordDtoList = hrRecordService.getHrDetails();

        if (hrRecordDtoList.isEmpty()) {
            LOGGER.warn("No HR records found getHrDetails()");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Returning {} HR records getHrDetails()::", hrRecordDtoList.size());
        return ResponseEntity.ok(hrRecordDtoList);
    }

    @Operation(summary = "Get HR details as record objects", description = "Returns a list of HR details as record objects.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of HR details as records"),
            @ApiResponse(responseCode = "204", description = "No HR records found")
    })
    @GetMapping("idAndCityRecord")
    public ResponseEntity<List<HrDetailsRecord>> getHrDetailsObject() {
        List<HrDetailsRecord> hrRecordDtoList = hrRecordService.getHrDetailsObject();

        if (hrRecordDtoList.isEmpty()) {
            LOGGER.warn("No HR records found getHrDetails()");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Returning {} HR records getHrDetails()::", hrRecordDtoList.size());
        /*try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        return ResponseEntity.ok(hrRecordDtoList);
    }

    @Operation(summary = "Get up to 1000 HR details as record objects", description = "Returns up to 1000 HR details as record objects.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of up to 1000 HR details as records"),
            @ApiResponse(responseCode = "204", description = "No HR records found")
    })
    @GetMapping("idAndCityRecord100")
    public ResponseEntity<List<HrDetailsRecord>> getHrDetailsObjectLimit1000() {
        LOGGER.info("Running in thread: {}", Thread.currentThread().getName());
        List<HrDetailsRecord> hrRecordDtoList = hrRecordService.getHrDetailsObjectLimit1000();

        if (hrRecordDtoList.isEmpty()) {
            LOGGER.warn("No HR records found getHrDetails()");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Returning {} HR records getHrDetails()::", hrRecordDtoList.size());
        /*try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        return ResponseEntity.ok(hrRecordDtoList);
    }

    @Operation(summary = "Get all HR records", description = "Returns all HR records from the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of all HR records"),
            @ApiResponse(responseCode = "204", description = "No HR records found")
    })
    @GetMapping("all")
    public ResponseEntity<List<HrRecordDto>> getAllHrRecord() {
        List<HrRecordDto> hrRecordDtoList = hrRecordService.getAllHrRecord();

        if (hrRecordDtoList.isEmpty()) {
            LOGGER.warn("No HR records found getAllHrRecord()");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Returning {} HR records getAllHrRecord()::", hrRecordDtoList.size());
        return ResponseEntity.ok(hrRecordDtoList);
    }

    @Operation(summary = "Get all HR records", description = "Returns all HR records from the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of all HR records"),
            @ApiResponse(responseCode = "204", description = "No HR records found")
    })
    @GetMapping("/stream")
    public ResponseBodyEmitter stream() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        Executors.newSingleThreadExecutor().submit(() -> hrRecordService.streamUsersToEmitter(emitter));
        return emitter;
    }

}
