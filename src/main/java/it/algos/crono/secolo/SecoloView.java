package it.algos.crono.secolo;

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
 * Date: sab, 04-mag-2024
 * Time: 19:59
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "secolo", layout = MainLayout.class)
@AView(menuGroup = Gruppo.CRONO, menuName = "Secoli")
public class SecoloView extends CrudView {


    /**
     * Costruttore alternativo invocato dalla sottoclasse concreta se si usa anche una formClazz specifico <br>
     * Mantiene il riferimento al CrudService Service (singleton) di questo Modulo <br>
     * Mantiene il riferimento ad una listClazz (CrudList) per creare l'istanza prototype <br>
     * Mantiene il riferimento ad una formClazz (CrudForm) per creare l'istanza prototype <br>
     */
    SecoloView(@Autowired SecoloService moduloCrudService) {
        super(moduloCrudService, SecoloList.class, SecoloForm.class);
    }


}// end of @Route CrudView class
