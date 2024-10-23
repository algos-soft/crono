package it.algos.crono.security;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import it.algos.vbase.security.LoginViewHeader;

public class CronoLoginViewHeader implements LoginViewHeader {

    @Override
    public Component getComponent() {
        Image image = new Image("images/evento-splash.svg", null);
        image.setMaxWidth("19.5rem");
        return image;
    }

}
