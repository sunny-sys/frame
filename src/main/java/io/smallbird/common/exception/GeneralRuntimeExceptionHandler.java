package io.smallbird.common.exception;

import io.smallbird.common.model.BaseResp;
import io.smallbird.common.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class GeneralRuntimeExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(GeneralRuntimeException.class)
    public BaseResp handleRRException(GeneralRuntimeException e) {
        return BaseResp.error(e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return Result.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return Result.info("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return Result.error();
    }
}
