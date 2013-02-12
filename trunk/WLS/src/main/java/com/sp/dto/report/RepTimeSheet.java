package com.sp.dto.report;

import com.sp.util.Constants;

import java.util.List;

/**
 * User: andrew
 * Date: 26.08.2010
 */
public class RepTimeSheet extends BaseReport {
    List<RepTimeSheetSection> timeSheetSections;

    public List<RepTimeSheetSection> getTimeSheetSections() {
        return timeSheetSections;
    }

    public void setTimeSheetSections(List<RepTimeSheetSection> timeSheetSections) {
        this.timeSheetSections = timeSheetSections;
    }

    public void calcTotals() {
        for (RepTimeSheetSection section : timeSheetSections) {
            TimeSheetJourneyRecord sumTimeSheetJourneyRecordTotal = new TimeSheetJourneyRecord();
            sumTimeSheetJourneyRecordTotal.setTotalType(Constants.TotalTypes.Sum);

            for (TimeSheetJourneyRecord record : section.getTimeSheetJourneyRecords()) {
                sumTimeSheetJourneyRecordTotal.setTravelledDuration(record.getTravelledDuration()
                        + sumTimeSheetJourneyRecordTotal.getTravelledDuration());

                sumTimeSheetJourneyRecordTotal.setDuration(record.getDuration()
                        + sumTimeSheetJourneyRecordTotal.getDuration());

                sumTimeSheetJourneyRecordTotal.setStoppedDuration(record.getStoppedDuration()
                        + sumTimeSheetJourneyRecordTotal.getStoppedDuration());

                sumTimeSheetJourneyRecordTotal.setTravelledDistance(record.getTravelledDistance()
                        + sumTimeSheetJourneyRecordTotal.getTravelledDistance());
            }

            TimeSheetJourneyRecord avgTimeSheetJourneyRecordTotal = new TimeSheetJourneyRecord();
            avgTimeSheetJourneyRecordTotal.setTotalType(Constants.TotalTypes.Avg);

            avgTimeSheetJourneyRecordTotal.setTravelledDuration(
                    sumTimeSheetJourneyRecordTotal.getTravelledDuration()
                            / (section.getTimeSheetJourneyRecords().size()));

            avgTimeSheetJourneyRecordTotal.setDuration(
                    sumTimeSheetJourneyRecordTotal.getDuration()
                            / (section.getTimeSheetJourneyRecords().size()));

            avgTimeSheetJourneyRecordTotal.setStoppedDuration(
                    sumTimeSheetJourneyRecordTotal.getStoppedDuration()
                            / (section.getTimeSheetJourneyRecords().size()));

            avgTimeSheetJourneyRecordTotal.setTravelledDistance(
                    sumTimeSheetJourneyRecordTotal.getTravelledDistance()
                            / (section.getTimeSheetJourneyRecords().size()));

            section.getTimeSheetJourneyRecords().add(sumTimeSheetJourneyRecordTotal);
            section.getTimeSheetJourneyRecords().add(avgTimeSheetJourneyRecordTotal);
        }
    }
}
