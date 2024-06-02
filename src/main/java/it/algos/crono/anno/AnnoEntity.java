package it.algos.crono.anno;

import it.algos.crono.secolo.*;
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
@Document(collection = "anno")
@AReset()
@AEntity()
public class AnnoEntity extends OrdineEntity {


    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AField(type = TypeField.text, caption = "Nome corrente")
    private String nome;

    @DBRef
    @ASearch(type = TypeSearch.comboClazz)
    @AFieldList(width = 6)
    @AField(type = TypeField.linkDBRef, linkClazz = SecoloEntity.class)
    private SecoloEntity secolo;

    @ASearch(type = TypeSearch.checkBox, typeCheckIniziale = TypeCheckBox.vero)
    @AFieldList(headerText = "D.C.")
    @AField(type = TypeField.booleano)
    private boolean dopoCristo;

    @ASearch(type = TypeSearch.checkBox)
    @AField(type = TypeField.booleano)
    private boolean bisestile;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
