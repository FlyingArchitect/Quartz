package com.demo.service;

import java.util.List;

import com.demo.dao.po.JobDetailPo;

public interface QuartzService {
	void addJob(JobDetailPo jobDetailPo);
	List<JobDetailPo> getList();
	void modfiy(JobDetailPo jobDetailPo);
	void pause(JobDetailPo JobDetailPo);
	void delete(JobDetailPo JobDetailPo);
	void resume(JobDetailPo JobDetailPo);
	

}
