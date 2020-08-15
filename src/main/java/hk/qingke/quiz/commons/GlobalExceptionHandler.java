package hk.qingke.quiz.commons;

import hk.qingke.quiz.exception.CommodityExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            CommodityExistException.class
    })
    public ResponseEntity<CommonResponse<Object>> commodityExceptionHandler(RuntimeException exception) {
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .code(ResponseCode.ERROR)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.badRequest().body(commonResponse);
    }
}
