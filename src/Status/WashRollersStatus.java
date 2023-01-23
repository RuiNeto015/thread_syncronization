package Status;

import enums.EnumWashRollersStatus;
import interfaces.IWashRollers;

/**
 *
 * @author Simão
 */
public class WashRollersStatus implements IWashRollers {

    private EnumWashRollersStatus status;

    /**
     * Default WashRollersStatus class constructor.
     */
    public WashRollersStatus() {
        this.status = EnumWashRollersStatus.STOPPED;
    }

    /**
     * Getter for the EnumWashRollersStatus.
     *
     * @return the EnumWashRollersStatus
     */
    @Override
    public EnumWashRollersStatus getWashRollersStatus() {
        return this.status;
    }

    /**
     * Setter for the EnumWashRollersStatus.
     *
     * @param status the EnumWashRollersStatus status
     */
    @Override
    public void setWashRollersStatus(EnumWashRollersStatus status) {
        this.status = status;
    }

}
