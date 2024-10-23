package it.algos.crono.anno;

import it.algos.crono.secolo.SecoloEntity;
import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.CheckBoxStatus;
import it.algos.vbase.enumeration.TBool;
import it.algos.vbase.enumeration.TypeBool;
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
@AEntity(sortProperty = "ordine")
public class AnnoEntity extends AbstractEntity {


    @Indexed(unique = true)
    @AFieldList(width = 5, headerText = "#")
    @AFieldForm(label = AnnoService.ORDINE, width = 20)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(headerText = "Anno")
    @AFieldForm(label = "Anno corrente")
    private String nome;

    @DBRef
//    @ARef(linkClazz = SecoloService.class, linkedProperty = "nome")
//    @ASearch(refSearchType = RefSearchType.combo, placeholder = "Secoli")
    @AFieldList(headerText = "Secolo", width = 12)
    @AFieldForm(clearButtonVisible = TBool.falso)
    private SecoloEntity secolo;

    @ABoolean(type = TypeBool.checkIcon)
    @ASearch(checkBoxInitialStatus = CheckBoxStatus.vero, tooltip = "Anni dopoCristo/anteCristo")
    @AFieldList(headerText = "D.C.")
    private boolean dopoCristo;

    @ABoolean(type = TypeBool.checkIcon)
    @ASearch(tooltip = "Anni bisestili (esistenti solo dopoCristo)")
    @AFieldList(headerText = "B")
    private boolean bisestile;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
