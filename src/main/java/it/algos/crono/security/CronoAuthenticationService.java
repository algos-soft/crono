package it.algos.crono.security;

import it.algos.vbase.backend.security.IAuthenticationService;
import it.algos.vbase.backend.service.MongoService;
import it.algos.vbase.backend.utility.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vbase.backend.security.Roles.*;

/**
 * Custom authentication service used to authenticate
 * the users in a way specific to the application.
 */
public class CronoAuthenticationService implements IAuthenticationService {

    private MongoTemplate mongoTemplate;

    @Override
    public Authentication authenticate(String name, String password) {
        doInjections();

        // db access to be implemented...
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        final UserDetails principal = new User(name, password, grantedAuths);

        return new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);

    }

    private void doInjections(){
        mongoTemplate = SpringContext.getBean(MongoTemplate.class);
    }

}
