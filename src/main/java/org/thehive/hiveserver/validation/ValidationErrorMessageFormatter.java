package org.thehive.hiveserver.validation;

import lombok.*;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Data
public class ValidationErrorMessageFormatter {

    private final FormatOptions formatOptions;

    public String format(@NotNull BindingResult bindingResult) {
        var message = new StringJoiner(formatOptions.delimiter, formatOptions.prefix, formatOptions.suffix);
        bindingResult.getAllErrors()
                .forEach(i -> message.add(i.getObjectName() + formatOptions.separator + i.getDefaultMessage()));
        return message.toString();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FormatOptions {

        private String delimiter;
        private String separator;
        private String prefix;
        private String suffix;

    }

}
