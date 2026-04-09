package com.banking.core.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * HTTP request/response logging filter.
 * Logs method, URI, status code, and execution time for observability.
 */
@Component
@Order(2)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int statusCode = wrappedResponse.getStatus();

            if (statusCode >= 500) {
                log.error("HTTP {} {} → {} ({}ms)",
                        request.getMethod(), request.getRequestURI(), statusCode, duration);
            } else if (statusCode >= 400) {
                log.warn("HTTP {} {} → {} ({}ms)",
                        request.getMethod(), request.getRequestURI(), statusCode, duration);
            } else {
                log.info("HTTP {} {} → {} ({}ms)",
                        request.getMethod(), request.getRequestURI(), statusCode, duration);
            }

            wrappedResponse.copyBodyToResponse();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator/health")
                || path.startsWith("/actuator/prometheus")
                || path.contains("/favicon.ico");
    }
}
