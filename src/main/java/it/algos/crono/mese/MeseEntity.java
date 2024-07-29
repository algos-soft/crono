package it.algos.crono.mese;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.crono.secolo.SecoloService;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
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
@AEntity(sortProperty = "ordine")
public class MeseEntity extends AbstractEntity {

    @Indexed(unique = true)
    @AFieldList(width = 4, headerText = "#")
    @AFieldForm()
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    private String sigla;

    @Indexed(unique = true)
    @ASearch()
    @AFieldForm(label = "Nome corrente")
    private String nome;

    private int giorni;

    @AFieldForm(label = "Primo giorno (da inizio anno) del mese")
    private int primo;

    @AFieldForm(label = "Ultimo giorno (da inizio anno) del mese")
    private int ultimo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
