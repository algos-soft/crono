package it.algos.crono.secolo;

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
@Document(collection = "secolo")
@AEntity(usaResetStartup = true)
public class SecoloEntity extends OrdineEntity {

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AField(type = TypeField.text, headerText = "Nome", caption = "Nome corrente")
    private String code;

    @AField(type = TypeField.integer, widthList = 6, caption = "Primo anno del secolo")
    private int inizio;

    @AField(type = TypeField.integer, widthList = 6, caption = "Ultimo anno del secolo")
    private int fine;

    @ASearch(type = TypeSearch.checkBox, typeCheckIniziale = TypeCheckBox.vero)
    @AField(type = TypeField.booleano, headerText = "D.C.")
    private boolean dopoCristo;


    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
