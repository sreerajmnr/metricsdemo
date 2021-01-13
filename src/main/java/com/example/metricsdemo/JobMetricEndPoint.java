package com.example.metricsdemo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import io.micrometer.core.instrument.MeterRegistry;

@Component
@Endpoint(id="job-status")
public class JobMetricEndPoint {

	public static final String CLAIM_BATCH_JOB_COMPLETED_STATUS = "COMPLETED";
	public static final String CLAIM_BATCH_JOB_STOPPED_STATUS = "STOPPED";
	public static final String CLAIM_BATCH_JOB_INPROGRESS_STATUS = "IN PROGRESS";
	
	private static final String CLAIM_BATCH_JOB_SUCESS_RECORD_COUNT = "claim.batch.job.success.record.count";
	
	private static final String CLAIM_BATCH_JOB_FAILED_RECORD_COUNT = "claim.batch.job.failed.record.count";
	
	private static final String CLAIM_BATCH_JOB_PROCESSED_RECORD_COUNT = "claim.batch.job.processed.record.count";
	
	private static final String CLAIM_BATCH_JOB_EXECUTION_STATUS = "claim.batch.job.execution.status";
	
	private static final String CLAIM_BATCH_JOB_TOTAL_RECORD_COUNT = "claim.batch.job.total.record.count";
		
	@Autowired
	private MeterRegistry meterRegistry;
	
	private String jobExecutionStatus;
	
	private long totalRecordCount;
	
	@ReadOperation
    public Map<String, Object> jobStatus() {
		
		long successCount = (long) meterRegistry.counter(CLAIM_BATCH_JOB_SUCESS_RECORD_COUNT).count();
		long failedCount = (long) meterRegistry.counter(CLAIM_BATCH_JOB_FAILED_RECORD_COUNT).count();
		long processedCount = successCount + failedCount;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CLAIM_BATCH_JOB_EXECUTION_STATUS, this.jobExecutionStatus);
		map.put(CLAIM_BATCH_JOB_PROCESSED_RECORD_COUNT, processedCount);
		map.put(CLAIM_BATCH_JOB_SUCESS_RECORD_COUNT, successCount);
		map.put(CLAIM_BATCH_JOB_FAILED_RECORD_COUNT, failedCount);
		map.put(CLAIM_BATCH_JOB_TOTAL_RECORD_COUNT, this.totalRecordCount);
        return map;
    }
	
	public void incrementSucessRecordCount() {
		meterRegistry.counter(CLAIM_BATCH_JOB_SUCESS_RECORD_COUNT).increment();
	}
	
	public void incrementFailedRecordCount() {
		meterRegistry.counter(CLAIM_BATCH_JOB_FAILED_RECORD_COUNT).increment();
	}
	
	public void setJobExecutionStatus(String jobExecutionStatus) {
		this.jobExecutionStatus = jobExecutionStatus;		
	}
	
	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
}
