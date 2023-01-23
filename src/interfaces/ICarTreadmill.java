package interfaces;

import enums.EnumCarTreadmillStatus;

/**
 *
 * @author Simão
 */
public interface ICarTreadmill {

    /**
     * Getter for the EnumCarTreadmillStatus.
     *
     * @return the carTreadmillStatus
     */
    EnumCarTreadmillStatus getCarTreadmillStatus();

    /**
     * Setter for the EnumCarTreadmillStatus.
     *
     * @param status the EnumCarTreadmillStatus status
     */
    void setCarTreadmillStatus(EnumCarTreadmillStatus status);
}
