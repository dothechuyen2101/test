package com.banking.core.web.filter;

import com.banking.common.constant.BankingConstants;
import com.banking.common.util.IdGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that generates and propagates request/trace IDs for distributed tracing.
 * Sets IDs in response headers and MDC for structured logging.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String traceId = extractOrGenerate(request, BankingConstants.HEADER_TRACE_ID);
        String requestId = extractOrGenerate(request, BankingConstants.HEADER_REQUEST_ID);
        String correlationId = extractOrGenerate(request, BankingConstants.HEADER_CORRELATION_ID);

        // Set in MDC for structured logging
        MDC.put(BankingConstants.MDC_TRACE_ID, traceId);
        MDC.put(BankingConstants.MDC_REQUEST_ID, requestId);

        // Set in response headers
        response.setHeader(BankingConstants.HEADER_TRACE_ID, traceId);
        response.setHeader(BankingConstants.HEADER_REQUEST_ID, requestId);
        response.setHeader(BankingConstants.HEADER_CORRELATION_ID, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(BankingConstants.MDC_TRACE_ID);
            MDC.remove(BankingConstants.MDC_REQUEST_ID);
        }
    }

    private String extractOrGenerate(HttpServletRequest request, String headerName) {
        String value = request.getHeader(headerName);
        if (StringUtils.hasText(value)) {
            return value;
        }
        return IdGenerator.traceId();
    }
}
