package it.algos.crono.boot;

import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.annotation.ALib;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: mer, 19-giu-2024
 * Time: 07:27
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@ALib()
public class CronoLib {

    public TreeGrid getMenu() {
        return null;
    }
}
