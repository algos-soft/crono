package it.algos.crono.secolo;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.TypeBool;
import it.algos.vbase.backend.enumeration.CheckBoxStatus;
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
@AEntity(sortProperty = "ordine")
public class SecoloEntity extends AbstractEntity {

    @Indexed(unique = true)
    @AFieldList(width = 4, headerText = "#")
    @AFieldForm(label = SecoloService.ORDINE)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(width = 12)
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @AFieldForm(label = "Primo anno del secolo")
    private int primo;

    @AFieldForm(label = "Ultimo anno del secolo")
    private int ultimo;

    @ABoolean()
    @ASearch(type = TypeSearch.checkBox, checkBoxInitialStatus = CheckBoxStatus.vero, checkBoxLabel = "DopoCristo")
    @AFieldList(headerText = "D.C.", width = 8)
    private boolean dopoCristo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
