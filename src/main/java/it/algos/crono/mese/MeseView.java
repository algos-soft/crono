package it.algos.crono.mese;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vbase.backend.annotation.IView;
import it.algos.vbase.backend.constant.Gruppo;
import it.algos.vbase.ui.view.AView;
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
@IView(menuGroup = Gruppo.CRONO, menuName = "Mesi", vaadin = VaadinIcon.CALENDAR)
public class MeseView extends AView {


    MeseView(@Autowired MeseService moduloService) {
        super(moduloService, MeseList.class, MeseForm.class);
    }

}// end of @Route CrudView class
