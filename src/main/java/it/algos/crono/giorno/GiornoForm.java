package it.algos.crono.giorno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.form.DefaultForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: lun, 16-set-2024
 * Time: 18:07
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GiornoForm<T extends AbstractEntity> extends DefaultForm<T> {


//    public GiornoForm() {
//    }

    public GiornoForm(T bean) {
        super(bean);
    }


}