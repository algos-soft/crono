package it.algos.crono.giorno;

import it.algos.crono.mese.MeseEntity;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.RefSearchType;
import it.algos.vbase.backend.enumeration.TBool;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "giorno")
@AReset()
@AEntity(sortProperty = "ordine")
public class GiornoEntity extends AbstractEntity {

    @Transient
    private static final int LAR = 12;

    @Indexed(unique = true)
    @AFieldList(width = 4, headerText = "#")
    @AFieldForm(label = GiornoService.ORDINE, width = 14)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(headerText = "Giorno")
    @AFieldForm(label = "Giorno corrente", width = LAR)
    private String nome;

    @DBRef
    @ASearch(refSearchType = RefSearchType.combo, placeholder = "Mesi")
    @AFieldList(headerText = "Mese", width = LAR)
    @AFieldForm(linkedProperty = "nome", placeholder = "Mesi", clearButtonVisible = TBool.vero)
    private MeseEntity mese;

    @AFieldForm(label = "Progressivo da inizio anno", width = LAR)
    private int trascorsi;

    @AFieldForm(label = "Mancanti a fine anno", width = LAR)
    private int mancanti;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
