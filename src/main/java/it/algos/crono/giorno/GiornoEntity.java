package it.algos.crono.giorno;

import it.algos.crono.mese.MeseEntity;
import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.RefSearchType;
import it.algos.vbase.enumeration.TBool;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "giorno")
@IReset()
@IEntity(pluralName = "Giorni", keyUniqueProperty = "nome", sortProperty = "ordine")
public class GiornoEntity extends AbstractEntity {

    @Transient
    private static final int LAR = 12;

    @Indexed(unique = true)
    @IFieldList(width = 4, headerText = "#")
    @IFieldForm(label = GiornoService.ORDINE, width = 14)
    private int ordine;

    @Indexed(unique = true)
    @IFieldSearch()
    @IFieldList(headerText = "Giorno")
    @IFieldForm(label = "Giorno corrente", width = LAR)
    private String nome;

    @DBRef
    @ICombo(sortProperty = "ordine")
    @IFieldSearch(refSearchType = RefSearchType.combo, placeholder = "Mesi")
    @IFieldList(linkedProperty = "nome", headerText = "Mese", width = 14)
    @IFieldForm(linkedProperty = "nome", placeholder = "Mesi", clearButtonVisible = TBool.falso)
    private MeseEntity mese;

    @IFieldForm(label = "Progressivo da inizio anno", width = LAR)
    private int trascorsi;

    @IFieldForm(label = "Mancanti a fine anno", width = LAR)
    private int mancanti;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
