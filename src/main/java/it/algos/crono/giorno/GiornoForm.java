package it.algos.crono.giorno;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.components.SimpleHorizontalLayout;
import it.algos.vbase.components.SimpleVerticalLayout;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.form.DefaultForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vbase.boot.BaseCost.FIELD_NAME_NOME;
import static it.algos.vbase.boot.BaseCost.FIELD_NAME_ORDINE;

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


    public GiornoForm(T bean) {
        super(bean);
    }

    protected void addFieldsToLayout() {
        HasComponents hasComponents = (HasComponents) body;
        SimpleHorizontalLayout colonne = new SimpleHorizontalLayout();
        SimpleVerticalLayout primaColonna = new SimpleVerticalLayout();
        SimpleVerticalLayout secondaColonna = new SimpleVerticalLayout();
        Component field;

        field = getField(FIELD_NAME_ORDINE);
        hasComponents.add(field);

        field = getField(FIELD_NAME_NOME);
        primaColonna.add(field);
        field = getField("mese");
        secondaColonna.add(field);

        field = getField("trascorsi");
        primaColonna.add(field);
        field = getField("mancanti");
        secondaColonna.add(field);

        colonne.add(primaColonna);
        colonne.add(secondaColonna);

        hasComponents.add(colonne);
    }

}