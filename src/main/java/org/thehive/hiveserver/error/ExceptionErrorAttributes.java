package org.thehive.hiveserver.error;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RequiredArgsConstructor
public class ExceptionErrorAttributes extends DefaultErrorAttributes {

    private final boolean includeMethod;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        var errorAttributes = super.getErrorAttributes(webRequest, options);
        errorAttributes.put("timestamp", System.currentTimeMillis());
        if (includeMethod && webRequest instanceof ServletWebRequest) {
            var servletWebRequest = (ServletWebRequest) webRequest;
            var method = servletWebRequest.getHttpMethod();
            if (method != null)
                errorAttributes.put("method", method.name());
        }
        return errorAttributes;
    }

}
