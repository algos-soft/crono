package it.algos.crono.mese;

import com.vaadin.flow.component.icon.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "mese")
@AEntity(usaResetStartup = true)
public class MeseEntity extends OrdineEntity {

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    private String sigla;

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AField(type = TypeField.text, caption = "Nome corrente")
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
