package org.thehive.hiveserver.session;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class SessionIdGenerator implements IdentifierGenerator {

    private static final int LEFT_LIMIT = 48;
    private static final int RIGHT_LIMIT = 58;
    private static final int LENGTH = 11;

    private final Random random = new Random();

    @Override
    public boolean supportsJdbcBatchInserts() {
        return false;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT)
                .limit(LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
