package Status;

import enums.EnumSprinklersAndDryersStatus;
import interfaces.ISprinklersAndDryers;

/**
 *
 * @author Victor
 */
public class SprinklersAndDryersStatus implements ISprinklersAndDryers {

    private EnumSprinklersAndDryersStatus status;

    /**
     * Default SprinklersAndDryersStatus class constructor.
     *
     */
    public SprinklersAndDryersStatus() {
        this.status = EnumSprinklersAndDryersStatus.STOPPED;
    }

    /**
     * Getter for the EnumSprinklersAndDryersStatus.
     *
     * @return the EnumSprinklersAndDryersStatus
     */
    @Override
    public EnumSprinklersAndDryersStatus getSprinklersAndDryersStatus() {
        return this.status;
    }

    /**
     * Setter for the EnumSprinklersAndDryersStatus.
     *
     * @param status the EnumSprinklersAndDryersStatus status
     */
    @Override
    public void setSprinklersAndDryersStatus(
            EnumSprinklersAndDryersStatus status) {

        this.status = status;
    }

}
