package interfaces;

import enums.EnumWashRollersStatus;

/**
 *
 * @author Simão
 */
public interface IWashRollers {

    /**
     * Getter for the EnumWashRollersStatus.
     *
     * @return the EnumWashRollersStatus
     */
    EnumWashRollersStatus getWashRollersStatus();

    /**
     * Setter for the EnumWashRollersStatus.
     *
     * @param status the EnumWashRollersStatus status
     */
    void setWashRollersStatus(EnumWashRollersStatus status);
}
