package it.algos.crono.anno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.CrudOperation;
import it.algos.vbase.backend.form.DefaultForm;
import it.algos.vbase.backend.logic.ModuloService;
import lombok.NonNull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;


/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: lun, 16-set-2024
 * Time: 14:35
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnnoForm<T extends AbstractEntity> extends DefaultForm<T> {


    public AnnoForm() {
    }

    public AnnoForm(@NonNull ModuloService<T> moduloService, T bean) {
        super(moduloService, bean);
    }

    public AnnoForm(@NonNull ModuloService<T> moduloService, T bean, CrudOperation operation) {
        super(moduloService, bean, operation);
    }

}