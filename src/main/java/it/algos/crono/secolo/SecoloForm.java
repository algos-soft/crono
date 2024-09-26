package it.algos.crono.secolo;

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
 * Time: 07:13
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SecoloForm<T extends AbstractEntity> extends DefaultForm<T> {


    public SecoloForm(T bean) {
        super(bean);
    }


}