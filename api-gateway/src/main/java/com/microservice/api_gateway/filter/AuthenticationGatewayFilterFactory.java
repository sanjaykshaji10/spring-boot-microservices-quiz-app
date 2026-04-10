package com.microservice.api_gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.microservice.api_gateway.service.JwtService;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    @Autowired
    private RouteValidator validator; // We will create this next

    @Autowired
    private JwtService jwtUtil;

    public AuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // 1. Check if the route is secured
            if (validator.isSecured.test(exchange.getRequest())) {
                // 2. Check if header contains token
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    // 3. Validate token
                    jwtUtil.validateToken(authHeader);

                    // 3. Extract Role for Authorization
                    String role = jwtUtil.getRoleFromToken(authHeader);
                    String path = exchange.getRequest().getURI().getPath();

                    // 4. Role-Based Access Control (RBAC) Logic
                    if (path.contains("/questions/add") && !"ROLE_ADMIN".equals(role)) {
                        // Block non-admins from adding questions
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }

                    exchange.getRequest().mutate()
                            .header("loggedInUser", jwtUtil.extractUsername(authHeader))
                            .header("userRole", role)
                            .build();

                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }
}
