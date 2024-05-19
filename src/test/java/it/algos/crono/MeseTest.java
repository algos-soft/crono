package it.algos.crono;

import it.algos.base.*;
import it.algos.crono.enumeration.*;
import it.algos.vbase.backend.enumeration.*;
import org.junit.jupiter.api.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 21-Oct-2023
 * Time: 21:05
 * <p>
 * Unit test di una enumeration che implementa Type <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enums")
@DisplayName("Enumeration Mese")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseTest extends EnumTest {


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.typeZero = Mese.getAllEnums().get(0);
        super.matrice = Mese.values();
    }



}


