package Status;

import enums.EnumSystemStatus;
import interfaces.ISystemStatus;

/**
 *
 * @author Victor
 */
public class SystemStatus implements ISystemStatus {

    private EnumSystemStatus status;

    /**
     * Default SystemStatus class constructor.
     */
    public SystemStatus() {
        this.status = EnumSystemStatus.CLOSED;
    }

    /**
     * Getter for the EnumSystemStatus.
     *
     * @return the EnumSystemStatus
     */
    @Override
    public EnumSystemStatus getSystemStatus() {
        return this.status;
    }

    /**
     * Setter for the EnumSystemStatus.
     *
     * @param status the EnumSystemStatus status
     */
    @Override
    public void setSystemStatus(EnumSystemStatus status) {
        this.status = status;
    }

}
