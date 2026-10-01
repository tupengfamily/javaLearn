package com.learning.springboot.security;

import com.learning.springboot.exception.BusinessException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 当前用户参数解析器
 * <p>
 * 配合 @CurrentUser 注解使用,从 request attribute 中取出 JwtAuthFilter 写入的 userId/username/roles。
 */
@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
        if (req == null) {
            throw new BusinessException(401, "无法获取请求上下文");
        }

        CurrentUser ann = parameter.getParameterAnnotation(CurrentUser.class);
        String field = ann == null ? "userId" : ann.value();
        Long userId = (Long) req.getAttribute(JwtAuthFilter.ATTR_USER_ID);
        String username = (String) req.getAttribute(JwtAuthFilter.ATTR_USERNAME);
        @SuppressWarnings("unchecked")
        Set<String> roles = (Set<String>) req.getAttribute(JwtAuthFilter.ATTR_ROLES);
        if (roles == null) roles = new HashSet<>();

        return switch (field) {
            case "username" -> username;
            case "roles" -> roles;
            case "userId" -> userId;
            default -> userId;
        };
    }
}