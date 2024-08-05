package it.algos.crono.security;

import com.vaadin.flow.router.Route;
import it.algos.vbase.backend.security.DummyLoginView;
import jakarta.annotation.PostConstruct;

@Route("login")
public class WikiLoginView extends DummyLoginView {

    @PostConstruct
    private void init(){
        add(loginForm);
    }

}
