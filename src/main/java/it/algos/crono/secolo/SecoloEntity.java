package it.algos.crono.secolo;

import it.algos.crono.anno.AnnoService;
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
    @AFieldForm(label = SecoloService.ORDINE, width = 20)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(width = 12,headerText = "Secolo")
    @AFieldForm(label = "Secolo corrente")
    private String nome;

    @AFieldForm(label = "Primo anno del secolo")
    private int primo;

    @AFieldForm(label = "Ultimo anno del secolo")
    private int ultimo;

    @ABoolean()
    @ASearch(checkBoxInitialStatus = CheckBoxStatus.vero, tooltip = "Secoli dopoCristo/anteCristo")
    @AFieldList(headerText = "D.C.")
    private boolean dopoCristo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
