package org.thehive.hiveserver.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorMessageProperties {

    private String delimiter;
    private String prefix;
    private String suffix;
    private String separator;
    private String source;
    private String encoding;
    private String uniquenessUsername;
    private String uniquenessEmail;


}
