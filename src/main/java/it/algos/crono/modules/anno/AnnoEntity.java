package it.algos.crono.modules.anno;

import it.algos.crono.modules.secolo.*;
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
@AEntity(collectionName = "anno", typeList = TypeList.hardCode)
public class AnnoEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthList = 6, caption = "Ordine a partire dal 1.000 a.C.")
    private int ordine;

    @AField(type = TypeField.text, widthList = 7, caption = "Nome corrente")
    private String nome;

    //    @DBRef
    @AField(type = TypeField.linkDBRef, widthList = 10, linkClazz = SecoloEntity.class)
    private SecoloEntity secolo;

    @AField(type = TypeField.booleano, headerText = "d.C.")
    private boolean dopoCristo;

    @AField(type = TypeField.booleano, headerText = "BS")
    private boolean bisestile;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
