package org.zh.thch.common.basic.exception;

import java.util.Objects;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/10/15 20:00:22
 */
public class BusinessException extends AbstractRuntimeException {
    private static final long serialVersionUID = 6421243017290623189L;

    private Integer code;
    private Object[] args;

    public BusinessException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public BusinessException() {
        super(ExceptionEnum.BUSINESS.getMessage());
        this.code = ExceptionEnum.BUSINESS.getCode();
    }

    public BusinessException(String message) {
        super(message);
        this.code = ExceptionEnum.BUSINESS.getCode();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(this.args);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BusinessException other = (BusinessException) obj;
        return super.equals(other) && Objects.deepEquals(this.args, other.args) && Objects.deepEquals(this.code, other.code);
    }
}
