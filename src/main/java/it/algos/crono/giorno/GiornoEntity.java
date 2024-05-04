package it.algos.crono.giorno;

import com.vaadin.flow.component.icon.*;
import it.algos.crono.mese.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "giorno")
public class GiornoEntity extends AbstractEntity {

    @Indexed(unique = true)
    @AField(type = TypeField.integer, headerText = "#", widthList = 3, caption = "Ordinamento da inizio anno")
    private int ordine;

    @Indexed(unique = true)
    @AField(type = TypeField.text, caption = "Nome corrente")
    private String nome;

    //    @DBRef
    @AField(type = TypeField.linkDBRef, linkClazz = MeseEntity.class)
    private MeseEntity mese;

    @AField(type = TypeField.integer, widthList = 6, headerIcon = VaadinIcon.STEP_BACKWARD, caption = "Progressivo da inizio anno")
    private int trascorsi;

    @AField(type = TypeField.integer, widthList = 6, headerIcon = VaadinIcon.STEP_FORWARD, caption = "Mancanti alla fine dell'anno")
    private int mancanti;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
