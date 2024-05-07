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
@AEntity(collectionName = "giorno", usaResetStartup = true)
public class GiornoEntity extends OrdineEntity {


    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AField(type = TypeField.text, headerText = "Nome",caption = "Nome corrente")
    private String code;

    //    @DBRef
    @ASearch(type = TypeSearch.comboClazz)
    @AField(type = TypeField.linkDBRef, linkClazz = MeseEntity.class)
    private MeseEntity mese;

    @AField(type = TypeField.integer, widthList = 6, headerIcon = VaadinIcon.STEP_BACKWARD, caption = "Progressivo da inizio anno")
    private int trascorsi;

    @AField(type = TypeField.integer, widthList = 6, headerIcon = VaadinIcon.STEP_FORWARD, caption = "Mancanti alla fine dell'anno")
    private int mancanti;

    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
