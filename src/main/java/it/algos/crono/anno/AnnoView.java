package it.algos.crono.anno;

import com.vaadin.flow.router.Route;
import it.algos.vbase.backend.annotation.AView;
import it.algos.vbase.ui.view.CrudView;
import it.algos.vbase.ui.view.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import static it.algos.crono.boot.CronoCost.GROUP_CRONO;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 18:52
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "anno", layout = MainLayout.class)
@AView(menuGroup = GROUP_CRONO, menuName = "Anni")
public class AnnoView extends CrudView {


    /**
     * Costruttore alternativo invocato dalla sottoclasse concreta se si usa anche una formClazz specifico <br>
     * Mantiene il riferimento al CrudService Service (singleton) di questo Modulo <br>
     * Mantiene il riferimento ad una listClazz (CrudList) per creare l'istanza prototype <br>
     * Mantiene il riferimento ad una formClazz (CrudForm) per creare l'istanza prototype <br>
     */
    AnnoView(@Autowired AnnoService moduloService) {
        super(moduloService, AnnoList.class, AnnoForm.class);
    }


}// end of @Route CrudView class
