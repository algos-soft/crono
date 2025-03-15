package it.algos.crono.mese;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "mese")
@IReset()
@IEntity(keyUniqueProperty = "sigla", sortProperty = "ordine", sortDirection = SortDirection.DESCENDING)
public class MeseEntity extends AbstractEntity {

    @Indexed(unique = true)
    @IFieldList(width = 4, headerText = "#")
    @IFieldForm()
    private int ordine;

    @Indexed(unique = true)
    private String sigla;

    @Indexed(unique = true)
    @IFieldSearch()
    @IFieldList(headerText = "Mese")
    @IFieldForm(label = "Mese corrente")
    private String nome;

    private int giorni;

    @IFieldForm(label = "Primo giorno del mese da inizio anno", width = 18)
    private int primo;

    @IFieldForm(label = "Ultimo giorno del mese da inizio anno", width = 18)
    private int ultimo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
