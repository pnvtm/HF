package com.pnvtm.hfs.cr.config;

import com.pnvtm.hfs.cr.domain.EventData;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class EventDataArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType() == EventData.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws IOException {

        String body = ((ServletWebRequest) webRequest).getRequest().getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        String[] parts = body.split(",");
        if (parts.length != 3) {
            return ResponseEntity.badRequest().build();
        }

        long timestamp = Long.parseLong(parts[0]);
        double dataX = Double.parseDouble(parts[1]);
        int dataY = Integer.parseInt(parts[2]);

        return new EventData(timestamp, dataX, dataY);
    }
}