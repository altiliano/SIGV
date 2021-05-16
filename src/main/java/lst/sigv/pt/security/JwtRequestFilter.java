package lst.sigv.pt.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.config.JwtUtils;
import lst.sigv.pt.service.impl.LstUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final LstUserDetailService userDetailService;
    private final JwtUtils jwtUtils;

    public JwtRequestFilter(LstUserDetailService userDetailService, JwtUtils jwtUtils) {
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String extractedToken = jwtUtils.extractToken(request);
        if (extractedToken != null) {
            handleRequestWithToken(extractedToken, request, response);
        }
        filterChain.doFilter(request, response);

    }

    private void handleRequestWithToken(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = jwtUtils.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = getUserDetails(username);
                if (jwtUtils.validateToken(token, userDetails)) {
                    createUsernamePasswordAuthenticationToken(request, userDetails);
                }
            }
        } catch (ExpiredJwtException exception) {
            if (!isCanIgnoreExpiredToken(request.getRequestURI(), request.getContextPath())) {
                handleExpiredToken(request, response, exception);
            }

        }

    }

    private void createUsernamePasswordAuthenticationToken(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }


    private UserDetails getUserDetails(String username) {
        return this.userDetailService.loadUserByUsername(username);
    }


    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException exception) throws IOException {
        if (jwtUtils.isCanRefreshToken(exception.getClaims().getExpiration())) {
            log.info("refreshing  the token");
            response.addHeader("refreshToken", "you need to refresh your token");
        } else {
            log.info("token can't be refreshed");
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

    }

    private boolean isCanIgnoreExpiredToken(String url, String baseUrl) {
        return url.equalsIgnoreCase(baseUrl + "/api/user/login") || url.equalsIgnoreCase(baseUrl + "/api/user/refreshToken");
    }


}
