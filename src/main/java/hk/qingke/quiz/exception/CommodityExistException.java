package hk.qingke.quiz.exception;

public class CommodityExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return "commodity existed";
    }
}
