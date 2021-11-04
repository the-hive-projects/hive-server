package org.thehive.hiveserver.validation;

import lombok.Data;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Data
public class ValidationErrorMessageFormatter {

    private final String delimiter;
    private final String separator;
    private final String prefix;
    private final String suffix;

    public String format(@NotNull BindingResult bindingResult) {
        var message = new StringJoiner(delimiter, prefix, suffix);
        bindingResult.getAllErrors()
                .forEach(i -> message.add(i.getObjectName() + separator + i.getDefaultMessage()));
        return message.toString();
    }

}
