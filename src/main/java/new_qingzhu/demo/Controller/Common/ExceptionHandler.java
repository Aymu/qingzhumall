package new_qingzhu.demo.Controller.Common;

import new_qingzhu.demo.Util.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest req) {
        Result result = new Result();
        result.setResultCode(500);
        //区分是否为自定义异常
        if (e instanceof Exception) {
            result.setMessage(e.getMessage());
        } else {
            result.setMessage("未知异常，请联系feigg");
        }
        //检查请求是否为ajax, 如果是 ajax 请求则返回 Result json串, 如果不是 ajax 请求则返回 error 视图
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            return result;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("url", req.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            modelAndView.addObject("author", "feigg");
            modelAndView.addObject("ltd", "青竹商城");
            modelAndView.setViewName("error/error");
            return modelAndView;
        }
    }
}
