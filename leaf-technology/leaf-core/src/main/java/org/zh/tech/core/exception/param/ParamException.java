package org.zh.tech.core.exception.param;

import org.zh.tech.core.exception.BusinessException;
import org.zh.thch.common.basic.exception.ExceptionEnum;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/12 18:45:56
 */
public class ParamException extends BusinessException {

    public ParamException(Integer code, String message) {
        super(code, message);
    }

    public ParamException(String message) {
        super(ExceptionEnum.PARAMETERS.getCode(), message);
    }

    public ParamException() {
        super(ExceptionEnum.PARAMETERS.getCode(), ExceptionEnum.PARAMETERS.getMessage());
    }
}
