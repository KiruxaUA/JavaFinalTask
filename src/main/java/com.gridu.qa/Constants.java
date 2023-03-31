package com.gridu.qa;

import java.time.LocalTime;

/**
 * The Constants class represents a training center working start time and working end time.
 */
public final class Constants {
    public static final LocalTime WORKING_START_TIME = LocalTime.of(10, 0);
    public static final LocalTime WORKING_END_TIME = LocalTime.of(18, 0);

    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class.");
    }
}