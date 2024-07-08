package it.algos.crono.mese;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vbase.backend.annotation.AView;
import it.algos.vbase.backend.constant.Gruppo;
import it.algos.vbase.ui.view.CrudView;
import it.algos.vbase.ui.view.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 05-Nov-2023
 * Time: 18:38
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "mese", layout = MainLayout.class)
@AView(menuGroup = Gruppo.CRONO, menuName = "Mesi", vaadin = VaadinIcon.CALENDAR)
public class MeseView extends CrudView {


    /**
     * Costruttore alternativo invocato dalla sottoclasse concreta se si usa anche una formClazz specifico <br>
     * Mantiene il riferimento al CrudService Service (singleton) di questo Modulo <br>
     * Mantiene il riferimento ad una listClazz (CrudList) per creare l'istanza prototype <br>
     */
    MeseView(@Autowired MeseService moduloService) {
        super(moduloService, MeseList.class);
    }

}// end of @Route CrudView class
