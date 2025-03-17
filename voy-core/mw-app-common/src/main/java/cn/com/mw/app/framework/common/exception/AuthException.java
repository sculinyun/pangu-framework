package cn.com.mw.app.framework.common.exception;

import cn.com.mw.app.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : ly
 * @date : 2022-11-18 12:26
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthException extends RuntimeException {

    /**
     * 业务错误码
     *
     * @see ServiceErrorCodeRange
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public AuthException() {
    }

    public AuthException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public AuthException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public AuthException(String message) {
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public AuthException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public AuthException setMessage(String message) {
        this.message = message;
        return this;
    }
}
