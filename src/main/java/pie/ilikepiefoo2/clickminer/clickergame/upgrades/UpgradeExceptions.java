package pie.ilikepiefoo2.clickminer.clickergame.upgrades;

public class UpgradeExceptions extends RuntimeException {
    public enum ErrorCode{
        NOT_ENOUGH_RESOURCES(1,"Not Enough Resources.")
        ;
        public final int STATUS_CODE;
        public final String MESSAGE;
        ErrorCode(int STATUS_CODE, String MESSAGE){
            this.STATUS_CODE = STATUS_CODE;
            this.MESSAGE = MESSAGE;
        }
    }
    private final ErrorCode errorCode;

    public UpgradeExceptions(ErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode()
    {
        return this.errorCode;
    }
}
