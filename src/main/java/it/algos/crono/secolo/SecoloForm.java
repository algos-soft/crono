package it.algos.crono.secolo;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.CrudOperation;
import it.algos.vbase.backend.form.AForm;
import it.algos.vbase.backend.logic.ModuloService;
import lombok.NonNull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: mar, 17-set-2024
 * Time: 07:13
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SecoloForm<T extends AbstractEntity> extends AForm<T> {


    public SecoloForm() {
    }

    public SecoloForm(@NonNull ModuloService<T> moduloService, T bean) {
        super(moduloService, bean);
    }

    public SecoloForm(@NonNull ModuloService<T> moduloService, T bean, CrudOperation operation) {
        super(moduloService, bean, operation);
    }


}