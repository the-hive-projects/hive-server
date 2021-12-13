package org.thehive.hiveserver.session;

import org.apache.commons.lang3.RandomStringUtils;

public class NumericalSessionIdGenerator implements SessionIdGenerator {

    private final int length;

    public NumericalSessionIdGenerator(int length) {
        if (length < 1)
            throw new IllegalArgumentException("Id length must be positive value");
        this.length = length;
    }

    @Override
    public String generate() {
        return RandomStringUtils.randomNumeric(length);
    }

}
