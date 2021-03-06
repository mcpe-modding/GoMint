package io.gomint.gui.element;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Dropdown {

    /**
     * Add a new option
     *
     * @param option to add
     * @return dropdown instance for chaining
     */
    Dropdown addOption( String option );

    /**
     * Add a new option with the option to select this as default
     *
     * @param option        to add
     * @param defaultOption true when this should be default, false when not
     * @return dropdown instance for chaining
     */
    Dropdown addOption( String option, boolean defaultOption );

}
