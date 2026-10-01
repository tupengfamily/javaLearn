package com.learning.springboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * JWT 鉴权过滤器
 * <p>
 * - 从 Authorization 头解析 Bearer Token
 * - 解析成功: 把 userId/username/roles 写入 request attribute,放行
 * - 解析失败: 直接返回 401 JSON 响应
 * <p>
 * 不需要登录的接口: /api/auth/**、/api/hello/**
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    public static final String ATTR_USER_ID = "currentUserId";
    public static final String ATTR_USERNAME = "currentUsername";
    public static final String ATTR_ROLES = "currentRoles";

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 非 /api/ 路径直接放行
        if (!path.startsWith("/api/")) return true;
        // 公开接口白名单(必须能匿名访问)
        // 注意: /api/auth/login 和 /api/auth/register 必须在此白名单,
        //       但 /api/auth/me 和 /api/auth/logout 需要鉴权, 走 filter
        return path.equals("/api/auth/login")
            || path.equals("/api/auth/register")
            || path.startsWith("/api/hello")
            || path.equals("/api/public/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain chain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(req);
        if (token == null || token.isEmpty()) {
            writeUnauthorized(resp, "缺少认证凭证");
            return;
        }
        try {
            Claims claims = jwtUtil.parse(token);
            Long userId = jwtUtil.extractUserId(claims);
            String username = claims.getSubject();
            Set<String> roles = jwtUtil.extractRoles(claims);

            req.setAttribute(ATTR_USER_ID, userId);
            req.setAttribute(ATTR_USERNAME, username);
            req.setAttribute(ATTR_ROLES, roles);

            chain.doFilter(req, resp);
        } catch (JwtException e) {
            log.warn("[JWT] 解析失败: {}", e.getMessage());
            writeUnauthorized(resp, "Token 无效或已过期");
        } catch (Exception e) {
            log.error("[JWT] 鉴权异常", e);
            writeUnauthorized(resp, "认证失败");
        }
    }

    private void writeUnauthorized(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setCharacterEncoding("UTF-8");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", 401);
        body.put("message", message);
        body.put("data", null);
        body.put("timestamp", java.time.LocalDateTime.now().toString());
        resp.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }
}