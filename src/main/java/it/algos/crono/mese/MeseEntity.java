package it.algos.crono.mese;

import com.vaadin.flow.component.icon.*;
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
@AEntity(collectionName = "mese", usaResetStartup = true)
public class MeseEntity extends AbstractEntity {

    @Indexed(unique = true)
    @AField(type = TypeField.integer, widthList = 6)
    private int ordine;

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AField(type = TypeField.text)
    private String code;

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textContains)
    @AField(type = TypeField.text)
    private String nome;

    @AField(type = TypeField.integer, widthList = 6)
    private int giorni;

    @AField(type = TypeField.integer, widthList = 6, headerIcon = VaadinIcon.STEP_BACKWARD, caption = "Primo giorno (annuo) del mese")
    private int primo;

    @AField(type = TypeField.integer, widthList = 6, headerIcon = VaadinIcon.STEP_FORWARD, caption = "Ultimo giorno (annuo) del mese")
    private int ultimo;


    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
