package com.soshow.ssi.studentClub.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soshow.ssi.base.BaseController;

import com.soshow.ssi.studentClub.dto.StudentClub;
import com.soshow.ssi.studentClub.dto.StudentClubCondition;
import com.soshow.ssi.studentClub.service.StudentClubService;
import com.soshow.ssi.enums.CommStatusEnum;
import com.soshow.ssi.enums.CommErrorEnum;
import com.soshow.ssi.util.MyResponse;

/**
 * 学生社团关联controller
 * @author xieb
 * @version 1.00
 * 2016/03/21
 */
@Controller
@RequestMapping(value = "studentClub")
public class StudentClubController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(StudentClubController.class);
	
	@Resource
	private StudentClubService studentClubService;
	
	/**
	 *通过id查找学生社团关联
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public MyResponse<StudentClub> findCityById(@PathVariable Integer id) {
		MyResponse<StudentClub> response = new MyResponse<StudentClub>();
		try {
			StudentClub studentClub= studentClubService.findById(id);
			response.setData(studentClub);
			logger.info("",studentClub);
			response.setStatusResponse(CommStatusEnum.FIND);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *添加学生社团关联
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public MyResponse<Void> addCity(@Valid @RequestBody StudentClub studentClub) {
		MyResponse<Void> response = new MyResponse<Void>();
		try {
			Integer id = studentClubService.add(studentClub);
			logger.info("",id);
			response.setStatusResponse(CommStatusEnum.ADD);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *删除学生社团关联
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public MyResponse<Void> deleteCity(@PathVariable Integer id) {
		MyResponse<Void> response = new MyResponse<Void>();
		try {
			int count = studentClubService.delete(id);
			logger.info("",count);
			response.setStatusResponse(CommStatusEnum.DELETE);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *修改学生社团关联
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public MyResponse<Void> updateCity(@Valid @RequestBody StudentClub studentClub) {
		MyResponse<Void> response = new MyResponse<Void>();
		try {
			int count = studentClubService.update(studentClub);
			logger.info("",count);
			response.setStatusResponse(CommStatusEnum.UPDATE);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *通过条件分页查询学生社团关联
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public MyResponse<List<StudentClub>> findCityPageByCondition(StudentClubCondition condition) {
		MyResponse<List<StudentClub>> response = new MyResponse<List<StudentClub>>();
		try {
			/*初始化分页查询*/
			if(condition.getPageSize()>0){
				condition.init();
			}
			int count =  studentClubService.countByCondition(condition);
			if(count==0){
				response.setStatusResponse(CommStatusEnum.NOFIND);
				return response;
			}
			response.setToken(count);
			List<StudentClub> studentClubList = studentClubService.findPageByCondition(condition);
			logger.info("",studentClubList);
			response.setData(studentClubList);
			response.setStatusResponse(CommStatusEnum.FIND);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}
}
