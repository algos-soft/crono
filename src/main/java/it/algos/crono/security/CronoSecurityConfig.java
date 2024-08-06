package it.algos.crono.security;

import com.vaadin.flow.component.Component;
import it.algos.vbase.backend.security.IAuthenticationService;
import it.algos.vbase.backend.security.LoginViewHeader;
import it.algos.vbase.backend.security.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure the Spring Security
 * <br>
 * This class is mandatory and it must extend the basic SecurityConfig class.
 * <br>
 * For a mimimum configuration, just define the login view.
 * <br>
 * If you want to handle the user authentication in your application, implement your
 * Authentication Service (IAuthenticationService)
 */
@Configuration
public class CronoSecurityConfig extends SecurityConfig {

    // Provide a custom authentication service
    // Optional, if not provided a standard AuthenticationService will be used
    @Bean
    public IAuthenticationService authService() {
        return new CronoAuthenticationService();
    }

    // Provide an optional header for the Login  view
    @Bean
    public LoginViewHeader loginViewHeader() {
        return new CronoLoginViewHeader();
    }

}
