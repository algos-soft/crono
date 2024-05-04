package it.algos.crono.modules.secolo;

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
@AEntity(collectionName = "secolo",usaResetStartup = true)
public class SecoloEntity extends AbstractEntity {


    @Indexed(unique = true)
    @AField(type = TypeField.integer, headerText = "#", widthList = 4, caption = "Ordinamento a partire dal XX secolo a.C.")
    private int ordine;

    @Indexed(unique = true)
    @AField(type = TypeField.text, caption = "Nome corrente")
    private String code;

    @AField(type = TypeField.integer, widthList = 6, caption = "Primo anno del secolo")
    private int inizio;

    @AField(type = TypeField.integer, widthList = 6, caption = "Ultimo anno del secolo")
    private int fine;

    @AField(type = TypeField.booleano, headerText = "d.C.")
    private boolean dopoCristo;


    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
