package it.algos.crono.anno;

import it.algos.crono.secolo.SecoloEntity;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.OrdineEntity;
import it.algos.vbase.backend.enumeration.TypeBool;
import it.algos.vbase.backend.enumeration.TypeCheckBox;
import it.algos.vbase.backend.enumeration.TypeSearch;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @AFieldList(width = 10)
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @DBRef
    @ASearch(type = TypeSearch.comboClazz, linkClazz = SecoloEntity.class, comboPlaceHolder = "Secoli")
    @AFieldList(width = 10)
    private SecoloEntity secolo;

    @ASearch(type = TypeSearch.checkBox, typeCheckIniziale = TypeCheckBox.vero, boxLabel = "DopoCristo")
    @ABoolean(type = TypeBool.checkIcon)
    @AFieldList(headerText = "D.C.")
    private boolean dopoCristo;

    @ASearch(type = TypeSearch.checkBox, boxLabel = "Bisestile")
    @ABoolean(type = TypeBool.checkIcon)
    @AFieldList(headerText = "B")
    private boolean bisestile;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
