package org.zh.thch.common.restful;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zh.thch.common.constant.Commons;

@Getter
@AllArgsConstructor
public enum ResultEnum implements Result {

    SUCCESS(Commons.RESULT_SUCCESS_CODE, Commons.RESULT_SUCCESS),
    FAILURE(Commons.RESULT_FAILURE_CODE, Commons.RESULT_FAILURE),
    UNKNOWN(Commons.RESULT_UNKNOWN_CODE, Commons.RESULT_UNKNOWN);

    private int code;
    private String message;
}
