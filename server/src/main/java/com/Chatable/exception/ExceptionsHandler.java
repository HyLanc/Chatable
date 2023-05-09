package com.Chatable.exception;


import com.Chatable.bean.CodeMsg;
import com.Chatable.dto.ResponseDTO;
import com.Chatable.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */

/**
 * 运行时触发异常捕获
 */
@ControllerAdvice
public class ExceptionsHandler {

    private  final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseDTO<Boolean> handle(RuntimeException e) {
        e.printStackTrace();
        if(!CommonUtil.isEmpty(e.getMessage())) {
            logger.info("异常信息={}", e.getMessage());
        }
        return ResponseDTO.errorByMsg(CodeMsg.SYSTEM_ERROR);
    }

}
