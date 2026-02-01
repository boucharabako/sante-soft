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
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    // Classes internes
    public static class Meta {

        private String disclaimer;
        private String terms;
        private String license;
        private String last_updated;
        private ResultsInfo results;

        public String getDisclaimer() {
            return disclaimer;
        }

        public void setDisclaimer(String disclaimer) {
            this.disclaimer = disclaimer;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLast_updated() {
            return last_updated;
        }

        public void setLast_updated(String last_updated) {
            this.last_updated = last_updated;
        }

        public ResultsInfo getResults() {
            return results;
        }

        public void setResults(ResultsInfo results) {
            this.results = results;
        }

    }

    public static class ResultsInfo {

        private int skip;
        private int limit;
        private int total;

        public int getSkip() {
            return skip;
        }

        public void setSkip(int skip) {
            this.skip = skip;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
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
        public List<String> getSpl_product_data_elements() {
            return spl_product_data_elements;
        }

        public void setSpl_product_data_elements(List<String> spl_product_data_elements) {
            this.spl_product_data_elements = spl_product_data_elements;
        }

        public List<String> getActive_ingredient() {
            return active_ingredient;
        }

        public void setActive_ingredient(List<String> active_ingredient) {
            this.active_ingredient = active_ingredient;
        }

        public String getSet_id() {
            return set_id;
        }

        public void setSet_id(String set_id) {
            this.set_id = set_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public OpenFDA getOpenfda() {
            return openfda;
        }

        public void setOpenfda(OpenFDA openfda) {
            this.openfda = openfda;
        }

        public List<String> getPurpose() {
            return purpose;
        }

        public void setPurpose(List<String> purpose) {
            this.purpose = purpose;
        }

        public List<String> getIndications_and_usage() {
            return indications_and_usage;
        }

        public void setIndications_and_usage(List<String> indications_and_usage) {
            this.indications_and_usage = indications_and_usage;
        }

        public List<String> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<String> warnings) {
            this.warnings = warnings;
        }

        public List<String> getDo_not_use() {
            return do_not_use;
        }

        public void setDo_not_use(List<String> do_not_use) {
            this.do_not_use = do_not_use;
        }

        public List<String> getAsk_doctor() {
            return ask_doctor;
        }

        public void setAsk_doctor(List<String> ask_doctor) {
            this.ask_doctor = ask_doctor;
        }

        public List<String> getAsk_doctor_or_pharmacist() {
            return ask_doctor_or_pharmacist;
        }

        public void setAsk_doctor_or_pharmacist(List<String> ask_doctor_or_pharmacist) {
            this.ask_doctor_or_pharmacist = ask_doctor_or_pharmacist;
        }

        public List<String> getWhen_using() {
            return when_using;
        }

        public void setWhen_using(List<String> when_using) {
            this.when_using = when_using;
        }

        public List<String> getStop_use() {
            return stop_use;
        }

        public void setStop_use(List<String> stop_use) {
            this.stop_use = stop_use;
        }

        public List<String> getPregnancy_or_breast_feeding() {
            return pregnancy_or_breast_feeding;
        }

        public void setPregnancy_or_breast_feeding(List<String> pregnancy_or_breast_feeding) {
            this.pregnancy_or_breast_feeding = pregnancy_or_breast_feeding;
        }

        public List<String> getKeep_out_of_reach_of_children() {
            return keep_out_of_reach_of_children;
        }

        public void setKeep_out_of_reach_of_children(List<String> keep_out_of_reach_of_children) {
            this.keep_out_of_reach_of_children = keep_out_of_reach_of_children;
        }

        public List<String> getDosage_and_administration() {
            return dosage_and_administration;
        }

        public void setDosage_and_administration(List<String> dosage_and_administration) {
            this.dosage_and_administration = dosage_and_administration;
        }

        public List<String> getStorage_and_handling() {
            return storage_and_handling;
        }

        public void setStorage_and_handling(List<String> storage_and_handling) {
            this.storage_and_handling = storage_and_handling;
        }

        public List<String> getInactive_ingredient() {
            return inactive_ingredient;
        }

        public void setInactive_ingredient(List<String> inactive_ingredient) {
            this.inactive_ingredient = inactive_ingredient;
        }

        public List<String> getQuestions() {
            return questions;
        }

        public void setQuestions(List<String> questions) {
            this.questions = questions;
        }

        public List<String> getPackage_label_principal_display_panel() {
            return package_label_principal_display_panel;
        }

        public void setPackage_label_principal_display_panel(List<String> package_label_principal_display_panel) {
            this.package_label_principal_display_panel = package_label_principal_display_panel;
        }

        public String getEffective_time() {
            return effective_time;
        }

        public void setEffective_time(String effective_time) {
            this.effective_time = effective_time;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

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
        private List<String> upc;
        private List<String> nui;
        private List<String> pharm_class_moa;
        private List<String> pharm_class_pe;
        private List<String> pharm_class_cs;
        private List<String> pharm_class_epc;
        private List<String> unii;

        // Getters & Setters
        public List<String> getApplication_number() {
            return application_number;
        }

        public void setApplication_number(List<String> application_number) {
            this.application_number = application_number;
        }

        public List<String> getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(List<String> brand_name) {
            this.brand_name = brand_name;
        }

        public List<Boolean> getIs_original_packager() {
            return is_original_packager;
        }

        public void setIs_original_packager(List<Boolean> is_original_packager) {
            this.is_original_packager = is_original_packager;
        }

        public List<String> getGeneric_name() {
            return generic_name;
        }

        public void setGeneric_name(List<String> generic_name) {
            this.generic_name = generic_name;
        }

        public List<String> getManufacturer_name() {
            return manufacturer_name;
        }

        public void setManufacturer_name(List<String> manufacturer_name) {
            this.manufacturer_name = manufacturer_name;
        }

        public List<String> getProduct_ndc() {
            return product_ndc;
        }

        public void setProduct_ndc(List<String> product_ndc) {
            this.product_ndc = product_ndc;
        }

        public List<String> getProduct_type() {
            return product_type;
        }

        public void setProduct_type(List<String> product_type) {
            this.product_type = product_type;
        }

        public List<String> getRoute() {
            return route;
        }

        public void setRoute(List<String> route) {
            this.route = route;
        }

        public List<String> getSubstance_name() {
            return substance_name;
        }

        public void setSubstance_name(List<String> substance_name) {
            this.substance_name = substance_name;
        }

        public List<String> getRxcui() {
            return rxcui;
        }

        public void setRxcui(List<String> rxcui) {
            this.rxcui = rxcui;
        }

        public List<String> getSpl_id() {
            return spl_id;
        }

        public void setSpl_id(List<String> spl_id) {
            this.spl_id = spl_id;
        }

        public List<String> getSpl_set_id() {
            return spl_set_id;
        }

        public void setSpl_set_id(List<String> spl_set_id) {
            this.spl_set_id = spl_set_id;
        }

        public List<String> getPackage_ndc() {
            return package_ndc;
        }

        public void setPackage_ndc(List<String> package_ndc) {
            this.package_ndc = package_ndc;
        }

        public List<String> getNui() {
            return nui;
        }

        public void setNui(List<String> nui) {
            this.nui = nui;
        }

        public List<String> getPharm_class_moa() {
            return pharm_class_moa;
        }

        public void setPharm_class_moa(List<String> pharm_class_moa) {
            this.pharm_class_moa = pharm_class_moa;
        }

        public List<String> getPharm_class_cs() {
            return pharm_class_cs;
        }

        public void setPharm_class_cs(List<String> pharm_class_cs) {
            this.pharm_class_cs = pharm_class_cs;
        }

        public List<String> getPharm_class_epc() {
            return pharm_class_epc;
        }

        public void setPharm_class_epc(List<String> pharm_class_epc) {
            this.pharm_class_epc = pharm_class_epc;
        }

        public List<String> getUnii() {
            return unii;
        }

        public void setUnii(List<String> unii) {
            this.unii = unii;
        }

        public List<String> getUpc() {
            return upc;
        }

        public void setUpc(List<String> upc) {
            this.upc = upc;
        }

        public List<String> getPharm_class_pe() {
            return pharm_class_pe;
        }

        public void setPharm_class_pe(List<String> pharm_class_pe) {
            this.pharm_class_pe = pharm_class_pe;
        }
        

    }
}
