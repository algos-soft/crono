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
    @AFieldForm(label = "Nome corrente")
    @AField(type = TypeField.text)
    private String nome;

    @DBRef
    @ASearch(type = TypeSearch.comboClazz)
    @AField(type = TypeField.linkDBRef, linkClazz = MeseEntity.class)
    private MeseEntity mese;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_BACKWARD)
    @AFieldForm(label = "Progressivo da inizio anno")
    @AField(type = TypeField.integer)
    private int trascorsi;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_FORWARD)
    @AFieldForm(label = "Mancanti alla fine dell'anno")
    @AField(type = TypeField.integer)
    private int mancanti;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
