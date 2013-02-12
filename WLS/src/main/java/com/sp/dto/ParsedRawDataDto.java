package com.sp.dto;

import com.sp.model.IncomingLogRecord;
import com.sp.model.RawIncomingLogRecord;

import java.util.List;

/**
 * User: andrew
 * Date: 18.08.2011
 */
public class ParsedRawDataDto {
    private RawIncomingLogRecord rawIncomingLogRecord;
    private List<IncomingLogRecord> parsedRecords;

    public RawIncomingLogRecord getRawIncomingLogRecord() {
        return rawIncomingLogRecord;
    }

    public void setRawIncomingLogRecord(RawIncomingLogRecord rawIncomingLogRecord) {
        this.rawIncomingLogRecord = rawIncomingLogRecord;
    }

    public List<IncomingLogRecord> getParsedRecords() {
        return parsedRecords;
    }

    public void setParsedRecords(List<IncomingLogRecord> parsedRecords) {
        this.parsedRecords = parsedRecords;
    }
}
