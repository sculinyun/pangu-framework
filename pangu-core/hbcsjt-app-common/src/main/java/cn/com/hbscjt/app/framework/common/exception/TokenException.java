package cn.com.hbscjt.app.framework.common.exception;

import cn.com.hbscjt.app.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : ly
 * @date : 2022-11-18 12:26
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenException extends RuntimeException {

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
    public TokenException() {
    }

    public TokenException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public TokenException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public TokenException(String message) {
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public TokenException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public TokenException setMessage(String message) {
        this.message = message;
        return this;
    }
}
