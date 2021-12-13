package org.thehive.hiveserver.session;

import org.apache.commons.lang3.RandomStringUtils;

public class NumericalSessionJoinIdGenerator implements SessionJoinIdGenerator {

    private final int length;

    public NumericalSessionJoinIdGenerator(int length) {
        if (length < 1)
            throw new IllegalArgumentException("Id length must be positive value");
        this.length = length;
    }

    @Override
    public String generate() {
        return RandomStringUtils.randomNumeric(length);
    }

}
