package it.algos.crono.anno;

import it.algos.crono.secolo.SecoloEntity;
import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.RefSearchType;
import it.algos.vbase.enumeration.TBool;
import it.algos.vbase.enumeration.TriState;
import it.algos.vbase.enumeration.TypeBool;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "anno")
@IReset()
@IEntity(pluralName = "Anni", keyUniqueProperty = "nome", sortProperty = "ordine")
public class AnnoEntity extends AbstractEntity {


    @Indexed(unique = true)
    @IFieldList(width = 5, headerText = "#")
    @IFieldForm(label = AnnoService.ORDINE, width = 20)
    private int ordine;

    @Indexed(unique = true)
    @IFieldSearch()
    @IFieldList(headerText = "Anno")
    @IFieldForm(label = "Anno corrente")
    private String nome;

    @DBRef
    @ICombo(sortProperty = "ordine")
    @IFieldSearch(refSearchType = RefSearchType.combo, placeholder = "Secoli")
    @IFieldList(linkedProperty = "nome",headerText = "Secolo", width = 12)
    @IFieldForm(linkedProperty = "nome", placeholder = "Mesi", clearButtonVisible = TBool.falso)
    private SecoloEntity secolo;

    @IBoolean(type = TypeBool.checkIcon)
    @IFieldSearch(checkBoxInitialStatus = TriState.vero, tooltip = "Anni dopoCristo/anteCristo")
    @IFieldList(headerText = "D.C.")
    private boolean dopoCristo;

    @IBoolean(type = TypeBool.checkIcon)
    @IFieldSearch(tooltip = "Anni bisestili (esistenti solo dopoCristo)")
    @IFieldList(headerText = "B")
    private boolean bisestile;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
