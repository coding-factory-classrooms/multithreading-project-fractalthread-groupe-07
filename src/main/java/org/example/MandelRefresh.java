package org.example;

import org.example.core.Template;

import java.util.HashMap;

public class MandelRefresh {
    public String MandelRefresh() {
        //DO the thing
        return Template.render("home.html", new HashMap<>());
    }
}
