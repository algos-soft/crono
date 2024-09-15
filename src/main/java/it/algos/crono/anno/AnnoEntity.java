package it.algos.crono.anno;

import it.algos.crono.secolo.SecoloEntity;
import it.algos.crono.secolo.SecoloService;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.CheckBoxStatus;
import it.algos.vbase.backend.enumeration.RefSearchType;
import it.algos.vbase.backend.enumeration.TypeBool;
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
    @AFieldForm(label = AnnoService.ORDINE)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(headerText = "Numero")
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @DBRef
    @ARef(linkClazz = SecoloService.class, linkedProperty = "nome")
    @ASearch(refSearchType = RefSearchType.combo)
    @AFieldList(headerText = "Secolo", width = 10)
    @AFieldForm(clearButtonCombo = false)
    private SecoloEntity secolo;

    @ABoolean(type = TypeBool.checkIcon)
    @ASearch(checkBoxInitialStatus = CheckBoxStatus.vero, label = "DopoCristo")
    @AFieldList(headerText = "D.C.", width = 8)
    private boolean dopoCristo;

    @ABoolean(type = TypeBool.checkIcon)
    @ASearch(label = "Bisestile")
    @AFieldList(headerText = "B")
    private boolean bisestile;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
