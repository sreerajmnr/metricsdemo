package com.example.metricsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
	
	@Autowired
	JobMetricEndPoint jobMetricEndPoint;
	
	@GetMapping("/start-job")
	public String startJob() {
		jobMetricEndPoint.incrementSucessRecordCount();
		jobMetricEndPoint.setJobExecutionStatus(JobMetricEndPoint.CLAIM_BATCH_JOB_INPROGRESS_STATUS);
		return "Job Started";
	}
	
	@GetMapping("/stop-job")
	public String stopJob() {
		jobMetricEndPoint.setJobExecutionStatus(JobMetricEndPoint.CLAIM_BATCH_JOB_STOPPED_STATUS);
		return "Job Stopped";
	}

}
