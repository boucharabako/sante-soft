package com.base.frame.carnet.sante.dtos;

/**
 *
 * @author Bouchara
 */
public class SpecialiteDTO {

    private String id;
    private String code;
    private String libelle;

    public SpecialiteDTO() {
    }

    public SpecialiteDTO(String id, String code, String libelle) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "SpecialiteDTO{" + "id=" + id + ", code=" + code + ", libelle=" + libelle + '}';
    }
}

