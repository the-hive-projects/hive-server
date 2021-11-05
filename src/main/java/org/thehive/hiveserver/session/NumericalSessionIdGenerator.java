package org.thehive.hiveserver.session;

import java.util.Random;

public class NumericalSessionIdGenerator implements SessionIdGenerator {

    private static final int LEFT_LIMIT = 48; //0
    private static final int RIGHT_LIMIT = 57; //9

    private final int length;
    private final Random random;

    public NumericalSessionIdGenerator(int length) {
        if (length < 1)
            throw new IllegalArgumentException("Length must be positive value");
        this.length = length;
        this.random = new Random();
    }

    @Override
    public String generate() {
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
