package Status;

import enums.EnumCarTreadmillStatus;
import interfaces.ICarTreadmill;

/**
 *
 * @author Rui Neto
 */
public class CarTreadmillStatus implements ICarTreadmill {

    private EnumCarTreadmillStatus status;

    /**
     * Default CarTreadmillStatus class constructor.
     *
     */
    public CarTreadmillStatus() {
        this.status = EnumCarTreadmillStatus.STOPPED;
    }

    /**
     * Getter for the EnumCarTreadmillStatus.
     *
     * @return the carTreadmillStatus
     */
    @Override
    public enums.EnumCarTreadmillStatus getCarTreadmillStatus() {
        return this.status;
    }

    /**
     * Setter for the EnumCarTreadmillStatus.
     *
     * @param status the EnumCarTreadmillStatus status
     */
    @Override
    public void setCarTreadmillStatus(enums.EnumCarTreadmillStatus status) {
        this.status = status;
    }
}
