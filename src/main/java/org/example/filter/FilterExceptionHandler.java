package org.example.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.exception.AuthenticationFailureException;
import org.example.metrics.prometheus.CustomMetricsService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class FilterExceptionHandler extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final CustomMetricsService customMetricsService;

    public FilterExceptionHandler(ObjectMapper objectMapper, CustomMetricsService customMetricsService) {
        this.objectMapper = objectMapper;
        this.customMetricsService = customMetricsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationFailureException e) {
            RestResponse restResponse =
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(), List.of(e.getMessage()));
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(restResponse));
        } finally {
            customMetricsService.incrementTotalRequestsSent();
        }
    }
}
