package it.algos.crono.mese;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.form.DefaultForm;
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

    public MeseForm(T bean) {
        super(bean);
    }


    protected Component buildBody() {
        FormLayout formLayout = new FormLayout();
        return formLayout;
    }

}