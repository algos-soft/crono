package it.algos.crono.anno;

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
 * Date: Mon, 06-Nov-2023
 * Time: 18:52
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "anno", layout = MainLayout.class)
@AView(menuGroup = Gruppo.CRONO, menuName = "Anni", vaadin = VaadinIcon.CALENDAR)
public class AnnoView extends CrudView {


    AnnoView(@Autowired AnnoService moduloService) {
        super(moduloService, AnnoList.class, AnnoForm.class);
    }


}// end of @Route CrudView class
