package it.algos.crono.mese;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
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
 * Date: mar, 17-set-2024
 * Time: 06:50
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MeseForm<T extends AbstractEntity> extends DefaultForm<T> {


    public MeseForm() {
    }

    public MeseForm(@NonNull ModuloService<T> moduloService, T bean) {
        super(moduloService, bean);
    }

    public MeseForm(@NonNull ModuloService<T> moduloService, T bean, CrudOperation operation) {
        super(moduloService, bean, operation);
    }

    protected Component buildBody() {
        FormLayout formLayout = new FormLayout();
        return formLayout;
    }

}