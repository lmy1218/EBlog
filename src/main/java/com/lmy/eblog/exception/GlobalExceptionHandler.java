package com.lmy.eblog.exception;
/**
 * @Project eblog
 * @Package com.lmy.eblog.exception
 * @author Administrator
 * @date 2020/10/5 12:48
 * @version V1.0
 */

import cn.hutool.json.JSONUtil;
import com.lmy.eblog.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Lmy
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理
 * @date 2020/10/5 12:48
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView handler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        // ajax 弹窗显示
        String header = req.getHeader("X-Requested-With");
        if(header != null  && "XMLHttpRequest".equals(header)) {
            res.setContentType("application/json;charset=UTF-8");
            try {
                res.getWriter().print(JSONUtil.toJsonStr(ResultDto.fail(e.getMessage())));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        // web处理
        ModelAndView view = new ModelAndView("error");
        view.addObject("msg", e.getMessage());
        return view;
    }

}
