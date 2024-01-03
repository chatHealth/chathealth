package chathealth.chathealth.exception;

public class BoardNotFoundException extends ChatHealthException {

    private static final String MESSAGE = "존재하지 않는 게시글 입니다.";

    public BoardNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
