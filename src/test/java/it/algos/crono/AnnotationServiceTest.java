package it.algos.crono;

import it.algos.base.service.AnnotationServiceBaseTest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: sab, 09-nov-2024
 * Time: 16:45
 */
@SpringBootTest(classes = {CronoApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Annotation Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnotationServiceTest extends AnnotationServiceBaseTest {
}
