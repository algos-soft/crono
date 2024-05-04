package it.algos.crono.modules.mese;

import com.vaadin.flow.component.icon.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "mese", keyPropertyName = "nome", typeList = TypeList.hardEnum)
public class MeseEntity extends AbstractEntity {

    @AField(type = TypeField.integer, widthList = 6)
    private int ordine;

    @AField(type = TypeField.text)
    private String sigla;

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
        return nome;
    }

}// end of Entity class
