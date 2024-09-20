package it.algos.crono.mese;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
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
    private String sigla;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(headerText = "Mese")
    @AFieldForm(label = "Mese corrente")
    private String nome;

    private int giorni;

    @AFieldForm(label = "Primo giorno del mese da inizio anno", width = 18)
    private int primo;

    @AFieldForm(label = "Ultimo giorno del mese da inizio anno", width = 18)
    private int ultimo;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
