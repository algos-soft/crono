package it.algos.crono.anno;

import it.algos.crono.giorno.GiornoService;
import it.algos.crono.secolo.SecoloEntity;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.entity.OrdineEntity;
import it.algos.vbase.backend.enumeration.RefSearchType;
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
public class AnnoEntity extends AbstractEntity {


    @Indexed(unique = true)
    @AFieldList(width = 4, headerText = "#")
    @AFieldForm(label = AnnoService.ORDINE)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @DBRef
//    @ARef(linkedProperty = "nome")
//    @ASearch(refSearchType = RefSearchType.combo)
    @AFieldList(width = 10)
    private SecoloEntity secolo;

    @ABoolean(type = TypeBool.checkIcon)
    @ASearch(type = TypeSearch.checkBox, checkBoxInitialStatus = TypeCheckBox.vero, checkBoxLabel = "DopoCristo")
    @AFieldList(headerText = "D.C.", width = 8)
    private boolean dopoCristo;

    @ABoolean(type = TypeBool.checkIcon)
    @ASearch(type = TypeSearch.checkBox, checkBoxLabel = "Bisestile")
    @AFieldList(headerText = "B")
    private boolean bisestile;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
