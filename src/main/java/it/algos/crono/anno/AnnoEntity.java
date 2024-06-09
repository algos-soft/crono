package it.algos.crono.anno;

import it.algos.crono.mese.MeseEntity;
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
    @AFieldList(width = 10)
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @DBRef
    @ASearch(type = TypeSearch.comboClazz, linkClazz = SecoloEntity.class, comboPlaceHolder = "Secoli")
    @AFieldList(width = 10)
    private SecoloEntity secolo;

    @ASearch(type = TypeSearch.checkBox, typeCheckIniziale = TypeCheckBox.vero, boxLabel = "DopoCristo")
    @AFieldList(headerText = "D.C.")
    private boolean dopoCristo;

    @ASearch(type = TypeSearch.checkBox, typeCheckIniziale = TypeCheckBox.vero, boxLabel = "Bisestile")
    private boolean bisestile;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
