package it.algos.crono.mese;

import com.vaadin.flow.component.icon.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "mese")
@AReset
@AEntity()
public class MeseEntity extends OrdineEntity {

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AFieldList()
    private String sigla;

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AFieldList(width = 12)
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @AFieldList(width = 6)
    private int giorni;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_BACKWARD)
    @AFieldForm(label = "Primo giorno (annuo) del mese")
    private int primo;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_FORWARD)
    @AFieldForm(label = "Ultimo giorno (annuo) del mese")
    private int ultimo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
