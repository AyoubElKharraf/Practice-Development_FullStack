package com.example.tp1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    // Page 1 : Affichage du formulaire pour saisir les informations
    @GetMapping("/")
    public String afficherFormulaire() {
        return "formulaire";
    }

    // Page 2 : Traitement et affichage des informations reçues
    @PostMapping("/afficher")
    public String traiterFormulaire(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam("filiere") String filiere,
            Model model) {

        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        model.addAttribute("email", email);
        model.addAttribute("filiere", filiere);

        return "affichage";
    }
}
