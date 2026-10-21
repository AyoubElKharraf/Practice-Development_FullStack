package com.example.tp1;

import com.example.tp1.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class Tp1ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testFormulaireReturnsFormView() {
        HomeController controller = new HomeController();
        String viewName = controller.afficherFormulaire();
        assertEquals("formulaire", viewName);
    }

    @Test
    void testAfficherReturnsDisplayView() {
        HomeController controller = new HomeController();
        Model model = new ConcurrentModel();
        String viewName = controller.traiterFormulaire("Dupont", "Jean", "jean@test.com", "M2I", model);

        assertEquals("affichage", viewName);
        assertEquals("Dupont", model.getAttribute("nom"));
        assertEquals("Jean", model.getAttribute("prenom"));
        assertEquals("jean@test.com", model.getAttribute("email"));
        assertEquals("M2I", model.getAttribute("filiere"));
    }
}

