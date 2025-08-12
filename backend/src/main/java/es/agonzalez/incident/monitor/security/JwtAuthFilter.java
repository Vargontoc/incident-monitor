package es.agonzalez.incident.monitor.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class JwtAuthFilter extends GenericFilter {
    @Autowired
    private JwtService jwtService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		var httpRequest = (HttpServletRequest) request;
        var header = httpRequest.getHeader("Authorization");
        
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            var token = header.substring(7);
            try {
                var claims = jwtService.parseToken(token).getBody();
                var auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), 
                    null, 
                    null
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }   
            
        


		chain.doFilter(request, response);
	}
}
