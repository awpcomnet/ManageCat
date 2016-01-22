package com.cat.manage.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @Description: 异常统一处理
 * @author 
 * @date 2015年11月17日 下午5:07:10
 */
public class StdExceptionHandler implements HandlerExceptionResolver {

	private static final String SUCCESS_RESULT_CODE    = "0";
	private static final String SUCCESS_RESULT_MESSAGE = "调用成功";
	private static final String UNKNOWN_RESULT_CODE    = "999999";
	private static final String UNKNOWN_RESULT_MESSAGE = "非预期异常";
	
	/**
	 * 异常处理
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		String resultCode    = SUCCESS_RESULT_CODE;
		String resultMessage = SUCCESS_RESULT_MESSAGE;
		
		if (ex instanceof ParameterException) {
			ParameterException pex = (ParameterException)ex;
			resultCode = pex.getExceptionCode();
			resultMessage = pex.getExceptionMessage();
			
		} else if (ex instanceof BusinessException) {
			BusinessException bex = (BusinessException)ex;
			resultCode = bex.getExceptionCode();
			resultMessage = bex.getExceptionMessage();
			
		} else {
			resultCode = UNKNOWN_RESULT_CODE;
			resultMessage = UNKNOWN_RESULT_MESSAGE + " " + ex.getMessage();
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setView(new MappingJackson2JsonView());
		mv.addObject("resultMessage", resultMessage);
		mv.addObject("resultCode", resultCode);
		return mv;
	}
	
}
