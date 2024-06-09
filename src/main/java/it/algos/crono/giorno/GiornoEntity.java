package it.algos.crono.giorno;

import com.vaadin.flow.component.icon.*;
import it.algos.crono.mese.*;
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
@Document(collection = "giorno")
@AReset()
@AEntity()
public class GiornoEntity extends OrdineEntity {


    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AFieldList(width = 10)
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @DBRef
    @ASearch(type = TypeSearch.comboClazz)
    @AFieldList(width = 10)
    private MeseEntity mese;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_BACKWARD)
    @AFieldForm(label = "Progressivo da inizio anno")
    private int trascorsi;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_FORWARD)
    @AFieldForm(label = "Mancanti alla fine dell'anno")
    private int mancanti;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
