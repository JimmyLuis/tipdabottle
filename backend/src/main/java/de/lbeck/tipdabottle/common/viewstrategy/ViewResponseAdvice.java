package de.lbeck.tipdabottle.common.viewstrategy;

import de.lbeck.tipdabottle.common.annotations.View;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ViewResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ViewStrategyResolver resolver;

    public ViewResponseAdvice(ViewStrategyResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(View.class);
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) return null;

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (body instanceof Page<?> page) {

            List<Object> mapped = page.getContent().stream()
                    .map(e -> resolver.resolve(auth, e).map(e))
                    .toList();

            return new PageImpl<>(
                    mapped,
                    page.getPageable(),
                    page.getTotalElements()
            );
        }

        if (body instanceof List<?> list) {
            return list.stream()
                    .map(e -> resolver.resolve(auth, e).map(e))
                    .toList();
        }

        return resolver.resolve(auth, body).map(body);
    }
}
