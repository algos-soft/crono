package it.algos.crono.giorno;

import com.vaadin.flow.router.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.view.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 15:34
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Giorni")
@Route(value = "giorno", layout = MainLayout.class)
@AView(menuGroup = MenuGroup.crono)
public class GiornoView extends CrudView {

    /**
     * Costruttore alternativo invocato dalla sottoclasse concreta se si usa anche una formClazz specifico <br>
     * Mantiene il riferimento al CrudService Service (singleton) di questo Modulo <br>
     * Mantiene il riferimento ad una listClazz (CrudList) per creare l'istanza prototype <br>
     */
    public GiornoView(@Autowired GiornoService moduloCrudService) {
        super(moduloCrudService, GiornoList.class);
    }

}// end of @Route CrudView class
