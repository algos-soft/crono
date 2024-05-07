package it.algos.crono.anno;

import it.algos.crono.*;
import it.algos.crono.secolo.*;
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
@AEntity(collectionName = "anno", usaResetStartup = true)
public class AnnoEntity extends OrdineEntity {


    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AField(type = TypeField.text, headerText = "Nome", caption = "Nome corrente")
    private String code;

    //    @DBRef
    @ASearch(type = TypeSearch.comboClazz)
    @AField(type = TypeField.linkDBRef, widthList = 10, linkClazz = SecoloEntity.class)
    private SecoloEntity secolo;

    @ASearch(type = TypeSearch.checkBox, typeCheckIniziale = TypeCheckBox.vero)
    @AField(type = TypeField.booleano, headerText = "D.C.")
    private boolean dopoCristo;

    @ASearch(type = TypeSearch.checkBox)
    @AField(type = TypeField.booleano)
    private boolean bisestile;


    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
