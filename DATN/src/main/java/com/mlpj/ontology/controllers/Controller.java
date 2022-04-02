package com.mlpj.ontology.controllers;

import com.mlpj.ontology.jenawork.InitJena;
import com.mlpj.ontology.util.Constant;
import net.minidev.json.JSONObject;
import org.apache.jena.vocabulary.RDF;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class Controller {
    @PostMapping("/person")
    public boolean insert(@RequestBody String name)  {
        return InitJena.insert(name);

    }

    @GetMapping("/person")
    public List<JSONObject> getPerson() {
        String queryString = Constant.PREFIX_QUERY +
                "SELECT ?X WHERE { ?X rdf:type foaf:Person.}";
        List<JSONObject> results = InitJena.getItems(queryString);
        return results;
    }

    @GetMapping("/festival")
    public List<JSONObject> getFestival() {
        String queryString = Constant.PREFIX_QUERY + "SELECT ?X ?Y WHERE { ?X  foaf:hasFestival ?Y.}";
        List<JSONObject> results = InitJena.getItems2(queryString);
        return results;
    }

    @GetMapping("/label")
    public List<JSONObject> getLabel(){
        String query = Constant.PREFIX_QUERY+
                "SELECT ?X WHERE { foaf:AbroadFestival rdfs:label ?X.}";
        List<JSONObject> results = InitJena.getItems0(query);
        return results;
    }
    @GetMapping("/chosenCapitalBy")
    public List<JSONObject> test(){
        String query = Constant.PREFIX_QUERY+
                "SELECT ?X  ?Y WHERE {?X foaf:chosenCapitalBy ?Y.}" ;
        List<JSONObject> results = InitJena.getItems2(query);
        return results;
    }
    @GetMapping("/hasPeriod")
    public List<JSONObject> hasPeriod(){

        String query = Constant.PREFIX_QUERY+
                "SELECT ?X  ?Y WHERE {?X foaf:hasPeriod ?Y.}" ;
        List<JSONObject> results = InitJena.getItems2(query);
        return results;
    }



}
