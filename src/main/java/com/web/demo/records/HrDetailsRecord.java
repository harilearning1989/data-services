package com.web.demo.records;

import java.io.Serializable;

public record HrDetailsRecord(long empId, String city) implements Serializable {
}
