package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.dao.po.JobDetailPo;
import com.demo.service.impl.QuartzServiceImpl;

@RequestMapping()
@Controller
public class HomeController {
	@Autowired
	private QuartzServiceImpl quartzServiceImpl;

//	 首页
//	http://localhost:8080/quartz/home
	@RequestMapping("home")
	public String toHome() {
		// quartzServiceImpl.test();
		// System.out.println("去首页");
		return "quartz/home";
	}

	@RequestMapping("toAdd")
	public String toAdd() {
		return "quartz/add";
	}

	@RequestMapping("doAdd")
	public String doAdd(JobDetailPo jobDetailPo) {
		System.out.println("jobDetailPo:" + jobDetailPo);
		quartzServiceImpl.modfiy(jobDetailPo);
		// quartzServiceImpl.startJob();框架会自动启动任务，所以不需要手动开启
		return "quartz/home";
	}

	/* 任务列表 */
	@RequestMapping("getList")
	@ResponseBody
	public List<JobDetailPo> getList() {
		List<JobDetailPo> li = quartzServiceImpl.getList();
		System.out.println("li:" + li);
		return li;
	}

	@RequestMapping("modfiy")
	public void modfiy() {
		// 修改和添加写一起了
	}
	
	@RequestMapping("pause")
	@ResponseBody
	public String pause(JobDetailPo jobDetailPo) {
		System.out.println("jobDetailPo:" + jobDetailPo);
		quartzServiceImpl.pause(jobDetailPo);
		return "1";
	}

	@RequestMapping("resume")
	@ResponseBody
	public String resume(JobDetailPo jobDetailPo) {
		quartzServiceImpl.resume(jobDetailPo);
		return "1";
	}

	@RequestMapping("delete")
	@ResponseBody
	public String delete(JobDetailPo jobDetailPo) {
		quartzServiceImpl.delete(jobDetailPo);
		return "1";
	}

}
