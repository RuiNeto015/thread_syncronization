package interfaces;

import enums.EnumSystemStatus;

/**
 *
 * @author Simão
 */
public interface ISystemStatus {

    /**
     * Getter for the EnumSystemStatus.
     *
     * @return the EnumSystemStatus
     */
    EnumSystemStatus getSystemStatus();

    /**
     * Setter for the EnumSystemStatus.
     *
     * @param status the EnumSystemStatus status
     */
    void setSystemStatus(EnumSystemStatus status);
}
