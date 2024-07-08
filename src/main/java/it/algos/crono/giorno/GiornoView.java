package it.algos.crono.giorno;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import it.algos.vbase.backend.annotation.AView;
import it.algos.vbase.backend.constant.Gruppo;
import it.algos.vbase.ui.view.CrudView;
import it.algos.vbase.ui.view.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 15:34
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "giorno", layout = MainLayout.class)
@AView(menuGroup = Gruppo.CRONO, menuName = "Giorni", vaadin = VaadinIcon.CALENDAR)
public class GiornoView extends CrudView {

    /**
     * Costruttore alternativo invocato dalla sottoclasse concreta se si usa anche una formClazz specifico <br>
     * Mantiene il riferimento al CrudService Service (singleton) di questo Modulo <br>
     * Mantiene il riferimento ad una listClazz (CrudList) per creare l'istanza prototype <br>
     * Mantiene il riferimento ad una formClazz (CrudForm) per creare l'istanza prototype <br>
     */
    GiornoView(@Autowired GiornoService moduloService) {
        super(moduloService, GiornoList.class, GiornoForm.class);
    }

}// end of @Route CrudView class
