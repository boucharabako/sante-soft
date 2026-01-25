package com.base.frame.carnet.sante.dtos;

/**
 * DTO pour Examen
 * @author Bouchara
 */
public class ExamenDTO {

    private String id;
    private String idConsultation;
    private String typeExamen;
    private String typeExamenLibelle; // Libellé du type d'examen (pour affichage)
    private String resultat;
    private String fichierJoint; // Base64 du fichier (pour upload uniquement)
    private String commentaire;

    // Métadonnées du fichier (stockées en base)
    private String cheminFichier; // Chemin relatif du fichier sur le disque
    private String nomFichier;    // Nom original du fichier
    private String typeMime;      // Type MIME (application/pdf, image/jpeg, etc.)

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdConsultation() {
        return idConsultation;
    }

    public void setIdConsultation(String idConsultation) {
        this.idConsultation = idConsultation;
    }

    public String getTypeExamen() {
        return typeExamen;
    }

    public void setTypeExamen(String typeExamen) {
        this.typeExamen = typeExamen;
    }

    public String getTypeExamenLibelle() {
        return typeExamenLibelle;
    }

    public void setTypeExamenLibelle(String typeExamenLibelle) {
        this.typeExamenLibelle = typeExamenLibelle;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getFichierJoint() {
        return fichierJoint;
    }

    public void setFichierJoint(String fichierJoint) {
        this.fichierJoint = fichierJoint;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getTypeMime() {
        return typeMime;
    }

    public void setTypeMime(String typeMime) {
        this.typeMime = typeMime;
    }
}

