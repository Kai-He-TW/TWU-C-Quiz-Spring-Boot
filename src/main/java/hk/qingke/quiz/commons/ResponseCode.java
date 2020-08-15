package hk.qingke.quiz.commons;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseCode {
    SUCCESS(1),
    ERROR(-1);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
