package it.algos.crono.boot;

import it.algos.crono.mese.*;
import it.algos.vbase.backend.boot.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import static it.algos.vbase.backend.boot.BaseVar.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.logic.*;
import it.algos.vbase.backend.wrapper.*;
import it.algos.vbase.ui.view.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 13:55
 */
@Service
@Component("cronoBoot")
public class CronoBoot extends BaseBoot {


    public CronoBoot() {
    }


    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixRoutes() {
        super.fixRoutes();

        List<Class> listaViewsProject;
        String message;
        String viewName;
        String pathView="it.algos.crono";

        if (Pref.usaMenuAutomatici.is()) {
            listaViewsProject = reflectionService.getSubClazz(CrudView.class,pathView);
            if (listaViewsProject != null) {
                for (Class clazz : listaViewsProject) {
                    if (annotationService.usaMenuAutomatico(clazz)) {
                        viewClazzListProject.add(clazz);
                        viewName = clazz.getSimpleName();
                        viewName = textService.levaCoda(viewName, SUFFIX_VIEW);
                        nameViewListProject.add(viewName);
                    }
                }
            }
            else {
                message = String.format("Non esiste nessuna view/route nel progetto [%s]", projectCurrent);
                logger.warn(new WrapLog().exception(new Exception(message)));
            }
        }
        else {
            viewClazzListProject.add(MeseView.class);
        }
    }


    protected boolean checkResetStartup() {
        if (super.checkResetStartup()) {
            for (CrudService modulo : crudServiceListProject) {
                modulo.checkResetStartup();
            }
        }

        return false;
    }

    protected void printInfoModuli() {
        // @todo  rimettere
        //        super.printInfoModuli();
        //
        //        for (CrudModulo modulo : crudModuloListProject) {
        //            modulo.checkReset();
        //        }
    }


}



