package org.zh.tech.core.exception.param;

import org.zh.thch.common.basic.exception.ExceptionEnum;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/12 18:46:27
 */
public class RequiredParamException extends ParamException{

    public RequiredParamException(String message) {
        super(ExceptionEnum.PARAMETERS_REQUIRED.getCode(), message);
    }

    public RequiredParamException() {
        super(ExceptionEnum.PARAMETERS_REQUIRED.getCode(), ExceptionEnum.PARAMETERS_REQUIRED.getMessage());
    }
}
