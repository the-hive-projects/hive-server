package org.thehive.hiveserver.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationProperties {

    private Message message;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {

        private String source;
        private String encoding;
        private ValidationErrorMessageFormatter.FormatOptions format;
        private Uniqueness uniqueness;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Uniqueness {
            private String username;
            private String email;
        }

    }

}
