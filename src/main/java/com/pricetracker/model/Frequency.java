package com.pricetracker.model;

public enum Frequency {
    DAILY("Daily check"),
    MORNING("Morning check (8 AM)"),
    NIGHT("Night check (10 PM)"),
    HOURLY("Hourly check"),
    CUSTOM("Custom schedule");

    private final String description;

    Frequency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Frequency fromString(String text) {
        for (Frequency freq : Frequency.values()) {
            if (freq.name().equalsIgnoreCase(text)) {
                return freq;
            }
        }
        return DAILY; // default
    }
}
