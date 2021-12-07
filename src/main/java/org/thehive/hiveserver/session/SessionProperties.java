package org.thehive.hiveserver.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class SessionProperties {

    private Id id;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {

        @Max(21)
        @Min(9)
        private int length;
        private Generator generator;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Generator {
            private Types type;

            public enum Types {
                NUMERICAL
            }

        }

    }

}
