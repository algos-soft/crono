package it.algos.crono.prova;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.view.*;
import jakarta.annotation.security.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.lineawesome.*;


import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: mer, 08-mag-2024
 * Time: 08:49
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Prova")
@Route(value = "provacrono", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.test)
public class ProvaCronoView extends CrudView {


    public ProvaCronoView(@Autowired ProvaCronoService moduloCrudService) {
        super(moduloCrudService, ProvaCronoList.class, ProvaCronoForm.class);
    }


}// end of @Route CrudView class
