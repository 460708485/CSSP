package com.soshow.ssi.sprovince.controller;

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

import com.soshow.ssi.sprovince.dto.Sprovince;
import com.soshow.ssi.sprovince.dto.SprovinceCondition;
import com.soshow.ssi.sprovince.service.SprovinceService;
import com.soshow.ssi.enums.CommStatusEnum;
import com.soshow.ssi.enums.CommErrorEnum;
import com.soshow.ssi.util.MyResponse;

/**
 * 省份controller
 * @author xieb
 * @version 1.00
 * 2016/03/21
 */
@Controller
@RequestMapping(value = "sprovince")
public class SprovinceController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(SprovinceController.class);
	
	@Resource
	private SprovinceService sprovinceService;
	
	/**
	 *通过id查找省份
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public MyResponse<Sprovince> findCityById(@PathVariable Integer id) {
		MyResponse<Sprovince> response = new MyResponse<Sprovince>();
		try {
			Sprovince sprovince= sprovinceService.findById(id);
			response.setData(sprovince);
			logger.info("",sprovince);
			response.setStatusResponse(CommStatusEnum.FIND);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *添加省份
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public MyResponse<Void> addCity(@Valid @RequestBody Sprovince sprovince) {
		MyResponse<Void> response = new MyResponse<Void>();
		try {
			Integer id = sprovinceService.add(sprovince);
			logger.info("",id);
			response.setStatusResponse(CommStatusEnum.ADD);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *删除省份
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public MyResponse<Void> deleteCity(@PathVariable Integer id) {
		MyResponse<Void> response = new MyResponse<Void>();
		try {
			int count = sprovinceService.delete(id);
			logger.info("",count);
			response.setStatusResponse(CommStatusEnum.DELETE);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *修改省份
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public MyResponse<Void> updateCity(@Valid @RequestBody Sprovince sprovince) {
		MyResponse<Void> response = new MyResponse<Void>();
		try {
			int count = sprovinceService.update(sprovince);
			logger.info("",count);
			response.setStatusResponse(CommStatusEnum.UPDATE);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}

	/**
	 *通过条件分页查询省份
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public MyResponse<List<Sprovince>> findCityPageByCondition(SprovinceCondition condition) {
		MyResponse<List<Sprovince>> response = new MyResponse<List<Sprovince>>();
		try {
			/*初始化分页查询*/
			if(condition.getPageSize()>0){
				condition.init();
			}
			int count =  sprovinceService.countByCondition(condition);
			if(count==0){
				response.setStatusResponse(CommStatusEnum.NOFIND);
				return response;
			}
			response.setToken(count);
			List<Sprovince> sprovinceList = sprovinceService.findPageByCondition(condition);
			logger.info("",sprovinceList);
			response.setData(sprovinceList);
			response.setStatusResponse(CommStatusEnum.FIND);
		} catch (Throwable t) {
			logger.error("系统错误", t);
			response.setErrorResponse(CommErrorEnum.Err03);
		}
		return response;
	}
}
