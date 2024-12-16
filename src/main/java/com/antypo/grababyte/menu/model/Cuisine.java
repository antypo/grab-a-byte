package com.antypo.grababyte.menu.model;

/**
 * Defines currently offered cuisines.
 */
public enum Cuisine {
    POLISH("Polish"),
    MEXICAN("Mexican"),
    ITALIAN("Italian");

    private final String displayName;

    Cuisine(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get enum from provided String.
     * Accounts for letters of different case, which is not the case with the default {@link Cuisine#valueOf(String)}.
     *
     * @param name name of the desired enum constant.
     * @return the enum constant with the specified name.
     */
    public static Cuisine getEnum(String name) {
        return valueOf(name.toUpperCase());
    }

    /**
     * Provides a display name instead of the enum value for better user experience.
     *
     * @return display name of the enum.
     */
    @Override
    public String toString() {
        return displayName;
    }
}
