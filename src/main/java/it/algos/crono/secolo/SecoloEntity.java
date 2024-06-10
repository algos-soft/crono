package it.algos.crono.secolo;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.OrdineEntity;
import it.algos.vbase.backend.enumeration.TypeBool;
import it.algos.vbase.backend.enumeration.TypeCheckBox;
import it.algos.vbase.backend.enumeration.TypeSearch;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "secolo")
@AReset()
@AEntity()
public class SecoloEntity extends OrdineEntity {

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AFieldList(width = 12)
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @AFieldList(width = 6)
    @AFieldForm(label = "Primo anno del secolo")
    private int inizio;

    @AFieldList(width = 6)
    @AFieldForm(label = "Ultimo anno del secolo")
    private int fine;

    @ASearch(type = TypeSearch.checkBox, typeCheckIniziale = TypeCheckBox.vero, boxLabel = "DopoCristo")
    @ABoolean(type = TypeBool.checkIcon)
    @AFieldList(headerText = "D.C.")
    private boolean dopoCristo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
