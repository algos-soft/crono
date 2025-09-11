package it.algos.crono.giorno;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.ui.view.AView;
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
@IView(menuGroup = Gruppo.CRONO, menuName = "Giorni", vaadin = VaadinIcon.CALENDAR)
public class GiornoCronoView extends AView {

    GiornoCronoView(@Autowired GiornoCronoService moduloService) {
        super(GiornoCronoEntity.class, moduloService, GiornoCronoList.class, GiornoCronoForm.class);
    }

}// end of @Route CrudView class
