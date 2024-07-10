package it.algos.crono.mese;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.OrdineEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "mese")
@AReset()
@AEntity()
public class MeseEntity extends OrdineEntity {

    @Indexed(unique = true)
    @ASearch()
    @AFieldList()
    private String sigla;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList()
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @AFieldList()
    private int giorni;

    @AFieldList(headerIcon = VaadinIcon.STEP_BACKWARD)
    @AFieldForm(label = "Primo giorno (annuo) del mese")
    private int primo;

    @AFieldList(headerIcon = VaadinIcon.STEP_FORWARD)
    @AFieldForm(label = "Ultimo giorno (annuo) del mese")
    private int ultimo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
