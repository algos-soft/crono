package it.algos.crono.anno;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.form.DefaultForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Map;


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

    public AnnoForm(T bean) {
        super(bean);
    }


    @Override
    public Map<String, AbstractField<?, ?>> registerFields() {
        return super.registerFields();
    }

    @Override
    protected void addFieldsToLayout() {
        fieldMap.keySet().stream().map(fieldMap::get).forEach(this::add);
    }

}