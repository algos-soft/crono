package it.algos.crono.home;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;
import it.algos.vbase.backend.annotation.AView;
import it.algos.vbase.backend.components.SimpleVerticalLayout;
import it.algos.vbase.backend.service.AnnotationService;
import it.algos.vbase.backend.service.MainLayoutService;
import it.algos.vbase.ui.view.MainLayout;
import it.algos.vbase.ui.wrapper.ASpan;
import org.springframework.beans.factory.annotation.Autowired;

import static it.algos.vbase.backend.boot.BaseCost.VUOTA;

@Route(value = VUOTA+"x", layout = MainLayout.class)
@AView(menuAutomatico = false)
public class CronoHomeView extends SimpleVerticalLayout {

    @Autowired
    private AnnotationService annotationService;

    @Autowired
    private MainLayoutService mainLayoutService;

    public CronoHomeView() {
        getStyle().set("align-items", "center");
        getStyle().set("justify-content", "center");

        setSizeFull();

        ASpan span = ASpan.text("Home page");
        span.size("3rem");
        span.bold();
        add(span);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        String title = "Home";
        Image img = annotationService.getModuleImage(CronoHomeView.class).orElse(null);
        mainLayoutService.setTopBarTitle(title, img);
    }

}