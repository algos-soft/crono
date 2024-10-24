package it.algos.crono.secolo;

import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.CheckBoxStatus;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "secolo")
@IReset()
@IEntity(keyProperty = "nome", sortProperty = "ordine")
public class SecoloEntity extends AbstractEntity {

    @Indexed(unique = true)
    @IFieldList(width = 4, headerText = "#")
    @IFieldForm(label = SecoloService.ORDINE, width = 18)
    private int ordine;

    @Indexed(unique = true)
    @ISearch()
    @IFieldList(width = 12, headerText = "Secolo")
    @IFieldForm(label = "Secolo corrente")
    private String nome;

    @IFieldForm(label = "Primo anno del secolo")
    private int primo;

    @IFieldForm(label = "Ultimo anno del secolo")
    private int ultimo;

    @IBoolean()
    @ISearch(checkBoxInitialStatus = CheckBoxStatus.vero, tooltip = "Secoli dopoCristo/anteCristo")
    @IFieldList(headerText = "D.C.")
    private boolean dopoCristo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
