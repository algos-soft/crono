package it.algos.crono.anno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.form.DefaultForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: lun, 16-set-2024
 * Time: 14:35
 * <p>
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnnoCronoForm<T extends AbstractEntity> extends DefaultForm<T> {


    public AnnoCronoForm(T bean) {
        super(bean);
    }


}