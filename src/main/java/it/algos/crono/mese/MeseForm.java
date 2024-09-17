package it.algos.crono.mese;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.components.SimpleHorizontalLayout;
import it.algos.vbase.backend.components.SimpleVerticalLayout;
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
 * Time: 06:50
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MeseForm<T extends AbstractEntity> extends AForm<T> {


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