package com.mlpj.ontology.jenawork;

import com.mlpj.ontology.util.Constant;
import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



public class InitJena {

    private static QueryExecution qe;
    private static String ontoFile = Constant.FILE;

    public static ResultSet execQuery(String queryString) {
        OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open(ontoFile);
            try {
                ontoModel.read(in, "");
                Query query = QueryFactory.create(queryString);
                //Execute the query and obtain results
                qe = QueryExecutionFactory.create(query, ontoModel);
                ResultSet results = qe.execSelect();
                return results;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JenaException je) {
            System.err.println("ERROR" + je.getMessage());
            je.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public static List<JSONObject> getItems(String queryString) {
        ResultSet resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        while (resultSet.hasNext()) {
            x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
            obj.put("id",x);
            obj.put("item",solution.get("X").toString().split("#")[1]);
            list.add(obj);
        }
        qe.close();
        return list;
    }
    public static List<JSONObject> getItems0(String queryString) {
        ResultSet resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        while (resultSet.hasNext()) {
            x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
            obj.put("id",x);
            obj.put("item",solution.get("X").toString().split("@")[0]);
            list.add(obj);
        }
        qe.close();
        return list;
    }


    public static List<JSONObject> getItems2(String queryString) {
        ResultSet resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        while (resultSet.hasNext()) {
            x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
            obj.put("id",x);
            obj.put("item",solution.get("X").toString().split("#")[1]);
            obj.put("itemY",solution.get("Y").toString().split("#")[1]);
            list.add(obj);
        }
        qe.close();
        return list;
    }

    public static boolean insert(String name){
        try{
            OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            InputStream in = FileManager.get().open(Constant.FILE);
            m.read(in, null);
            Resource a0 = m.createResource(Constant.PREFIX + name);
            Resource a1 = m.createResource(Constant.PREFIX + "Person");

            m.add(a1, RDF.type, OWL.Class);
            m.add(a0, RDF.type, a1);

            OutputStream output = new FileOutputStream(Constant.FILE);
            m.writeAll(output, "RDF/XML", null);
            output.close();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }


}
