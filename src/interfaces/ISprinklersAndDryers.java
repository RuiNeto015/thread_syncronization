package interfaces;

import enums.EnumSprinklersAndDryersStatus;

/**
 *
 * @author Simão
 */
public interface ISprinklersAndDryers {

    /**
     * Getter for the EnumSprinklersAndDryersStatus.
     *
     * @return the EnumSprinklersAndDryersStatus
     */
    EnumSprinklersAndDryersStatus getSprinklersAndDryersStatus();

    /**
     * Setter for the EnumSprinklersAndDryersStatus.
     *
     * @param status the EnumSprinklersAndDryersStatus status
     */
    void setSprinklersAndDryersStatus(EnumSprinklersAndDryersStatus status);
}
