package it.algos.crono.mese;

import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "mese")
@IReset()
@IEntity(keyProperty = "sigla", sortProperty = "ordine", sortDirection = SortDirection.DESCENDING)
public class MeseEntity extends AbstractEntity {

    @Indexed(unique = true)
    @IFieldList(width = 4, headerText = "#")
    @IFieldForm()
    private int ordine;

    @Indexed(unique = true)
    private String sigla;

    @Indexed(unique = true)
    @ISearch()
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

    // Metodo che converte l'oggetto Entity in un Document MongoDB
    public org.bson.Document toDocument() {
        return new org.bson.Document("id", getId())
                .append("ordine", getOrdine())
                .append("sigla", getSigla())
                .append("nome", getNome())
                .append("giorni", getGiorni())
                .append("primo", getPrimo())
                .append("ultimo", getUltimo());
    }

}// end of Entity class
