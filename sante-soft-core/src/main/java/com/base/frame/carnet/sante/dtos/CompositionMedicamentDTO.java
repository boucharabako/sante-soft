/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.dtos;
import java.util.List;

/**
 *
 * @author Bouchara
 */
public class CompositionMedicamentDTO {
    
    private Meta meta;
    private List<Result> results;

    // Getters & Setters
    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }
    public List<Result> getResults() { return results; }
    public void setResults(List<Result> results) { this.results = results; }

    // Classes internes
    public static class Meta {
        private String disclaimer;
        private String terms;
        private String license;
        private String last_updated;
        private ResultsInfo results;

        public String getDisclaimer() { return disclaimer; }
        public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }
        public String getTerms() { return terms; }
        public void setTerms(String terms) { this.terms = terms; }
        public String getLicense() { return license; }
        public void setLicense(String license) { this.license = license; }
        public String getLast_updated() { return last_updated; }
        public void setLast_updated(String last_updated) { this.last_updated = last_updated; }
        public ResultsInfo getResults() { return results; }
        public void setResults(ResultsInfo results) { this.results = results; }
    }

    public static class ResultsInfo {
        private int skip;
        private int limit;
        private int total;

        public int getSkip() { return skip; }
        public void setSkip(int skip) { this.skip = skip; }
        public int getLimit() { return limit; }
        public void setLimit(int limit) { this.limit = limit; }
        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }
    }

    public static class Result {
        private List<String> spl_product_data_elements;
        private List<String> active_ingredient;
        private List<String> purpose;
        private List<String> indications_and_usage;
        private List<String> warnings;
        private List<String> do_not_use;
        private List<String> ask_doctor;
        private List<String> ask_doctor_or_pharmacist;
        private List<String> when_using;
        private List<String> stop_use;
        private List<String> pregnancy_or_breast_feeding;
        private List<String> keep_out_of_reach_of_children;
        private List<String> dosage_and_administration;
        private List<String> storage_and_handling;
        private List<String> inactive_ingredient;
        private List<String> questions;
        private List<String> package_label_principal_display_panel;
        private String set_id;
        private String id;
        private String effective_time;
        private String version;
        private OpenFDA openfda;

        // Getters & Setters pour chaque champ
        public List<String> getSpl_product_data_elements() { return spl_product_data_elements; }
        public void setSpl_product_data_elements(List<String> spl_product_data_elements) { this.spl_product_data_elements = spl_product_data_elements; }
        public List<String> getActive_ingredient() { return active_ingredient; }
        public void setActive_ingredient(List<String> active_ingredient) { this.active_ingredient = active_ingredient; }
        public String getSet_id() { return set_id; }
        public void setSet_id(String set_id) { this.set_id = set_id; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public OpenFDA getOpenfda() { return openfda; }
        public void setOpenfda(OpenFDA openfda) { this.openfda = openfda; }

    }

    public static class OpenFDA {
        private List<String> application_number;
        private List<String> brand_name;
        private List<String> generic_name;
        private List<String> manufacturer_name;
        private List<String> product_ndc;
        private List<String> product_type;
        private List<String> route;
        private List<String> substance_name;
        private List<String> rxcui;
        private List<String> spl_id;
        private List<String> spl_set_id;
        private List<String> package_ndc;
        private List<Boolean> is_original_packager;
        private List<String> nui;
        private List<String> pharm_class_moa;
        private List<String> pharm_class_cs;
        private List<String> pharm_class_epc;
        private List<String> unii;

        // Getters & Setters
        public List<String> getApplication_number() { return application_number; }
        public void setApplication_number(List<String> application_number) { this.application_number = application_number; }
        public List<String> getBrand_name() { return brand_name; }
        public void setBrand_name(List<String> brand_name) { this.brand_name = brand_name; }
        public List<Boolean> getIs_original_packager() { return is_original_packager; }
        public void setIs_original_packager(List<Boolean> is_original_packager) { this.is_original_packager = is_original_packager; }

    }
}
