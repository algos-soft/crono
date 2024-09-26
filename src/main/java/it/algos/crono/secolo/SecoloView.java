package it.algos.crono.secolo;

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
 * Date: sab, 04-mag-2024
 * Time: 19:59
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "secolo", layout = MainLayout.class)
@AView(menuGroup = Gruppo.CRONO, menuName = "Secoli", vaadin = VaadinIcon.CALENDAR)
public class SecoloView extends CrudView {


    SecoloView(@Autowired SecoloService moduloCrudService) {
        super(moduloCrudService, SecoloList.class, SecoloForm.class);
    }


}// end of @Route CrudView class
