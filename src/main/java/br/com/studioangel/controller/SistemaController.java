package br.com.studioangel.controller;

import org.springframework.stereotype.Controller;

@Controller
public class SistemaController {

    public boolean autenticar(String usuario, String senha) {
        return "admin".equals(usuario) && "1234".equals(senha);
    }
}
