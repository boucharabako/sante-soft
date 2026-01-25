package com.base.frame.carnet.sante.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service de gestion du stockage des fichiers sur le disque
 * @author Bouchara
 */
@Service
public class FileStorageService {
    
    // Répertoire racine de stockage (configurable dans application.properties)
    @Value("${file.upload.dir:D:/MemoireNetbeans/uploads}")
    private String uploadDir;
    
    /**
     * Sauvegarder un fichier sur le disque
     * @param base64Data Données du fichier en Base64
     * @param nomFichierOriginal Nom original du fichier
     * @param typeMime Type MIME du fichier
     * @param sousRepertoire Sous-répertoire (ex: "examens", "prescriptions")
     * @param numeroCarnetPatient Numéro de carnet du patient
     * @param nomPatient Nom du patient
     * @return Chemin relatif du fichier sauvegardé
     * @throws IOException En cas d'erreur d'écriture
     */
    public String sauvegarderFichier(String base64Data, String nomFichierOriginal, String typeMime, String sousRepertoire, String numeroCarnetPatient, String nomPatient) throws IOException {
        if (base64Data == null || base64Data.isEmpty()) {
            return null;
        }
        
        // Nettoyer le Base64 (enlever le préfixe data:image/png;base64, si présent)
        String base64Clean = base64Data;
        if (base64Data.contains(",")) {
            base64Clean = base64Data.split(",")[1];
        }
        
        // Décoder le Base64
        byte[] fileBytes = Base64.getDecoder().decode(base64Clean);
        
        // Créer la structure de répertoires : uploads/examens/2026/01/
        LocalDate today = LocalDate.now();
        String annee = today.format(DateTimeFormatter.ofPattern("yyyy"));
        String mois = today.format(DateTimeFormatter.ofPattern("MM"));
        
        String cheminRepertoire = uploadDir + File.separator + sousRepertoire + 
                                  File.separator + annee + File.separator + mois;
        
        // Créer les répertoires s'ils n'existent pas
        File repertoire = new File(cheminRepertoire);
        if (!repertoire.exists()) {
            repertoire.mkdirs();
            System.out.println("📁 Répertoire créé: " + cheminRepertoire);
        }
        
        // Générer un nom de fichier unique : numeroCarnet_nomPatient_nomOriginal
        String extension = extraireExtension(nomFichierOriginal);
        String nomFichierUnique = nettoyerNomFichier(numeroCarnetPatient) + "_" +
                                  nettoyerNomFichier(nomPatient) + "_" +
                                  nettoyerNomFichier(nomFichierOriginal);
        
        // Chemin complet du fichier
        String cheminComplet = cheminRepertoire + File.separator + nomFichierUnique;
        
        // Écrire le fichier sur le disque
        try (FileOutputStream fos = new FileOutputStream(cheminComplet)) {
            fos.write(fileBytes);
            System.out.println("✅ Fichier sauvegardé: " + cheminComplet + " (" + fileBytes.length + " octets)");
        }
        
        // Retourner le chemin relatif (pour stockage en base)
        String cheminRelatif = sousRepertoire + "/" + annee + "/" + mois + "/" + nomFichierUnique;
        return cheminRelatif;
    }
    
    /**
     * Lire un fichier depuis le disque
     * @param cheminRelatif Chemin relatif du fichier
     * @return Contenu du fichier en bytes
     * @throws IOException En cas d'erreur de lecture
     */
    public byte[] lireFichier(String cheminRelatif) throws IOException {
        if (cheminRelatif == null || cheminRelatif.isEmpty()) {
            return null;
        }
        
        String cheminComplet = uploadDir + File.separator + cheminRelatif.replace("/", File.separator);
        Path path = Paths.get(cheminComplet);
        
        if (!Files.exists(path)) {
            throw new IOException("Fichier non trouvé: " + cheminComplet);
        }
        
        return Files.readAllBytes(path);
    }
    
    /**
     * Supprimer un fichier du disque
     * @param cheminRelatif Chemin relatif du fichier
     * @return true si supprimé, false sinon
     */
    public boolean supprimerFichier(String cheminRelatif) {
        if (cheminRelatif == null || cheminRelatif.isEmpty()) {
            return false;
        }
        
        String cheminComplet = uploadDir + File.separator + cheminRelatif.replace("/", File.separator);
        File fichier = new File(cheminComplet);
        
        if (fichier.exists()) {
            boolean supprime = fichier.delete();
            if (supprime) {
                System.out.println("🗑️ Fichier supprimé: " + cheminComplet);
            }
            return supprime;
        }
        
        return false;
    }
    
    /**
     * Vérifier si un fichier existe
     * @param cheminRelatif Chemin relatif du fichier
     * @return true si le fichier existe, false sinon
     */
    public boolean fichierExiste(String cheminRelatif) {
        if (cheminRelatif == null || cheminRelatif.isEmpty()) {
            return false;
        }
        
        String cheminComplet = uploadDir + File.separator + cheminRelatif.replace("/", File.separator);
        return new File(cheminComplet).exists();
    }
    
    /**
     * Extraire l'extension d'un nom de fichier
     */
    private String extraireExtension(String nomFichier) {
        if (nomFichier == null || !nomFichier.contains(".")) {
            return "";
        }
        return nomFichier.substring(nomFichier.lastIndexOf("."));
    }
    
    /**
     * Nettoyer un nom de fichier (enlever les caractères spéciaux)
     */
    private String nettoyerNomFichier(String nomFichier) {
        // Remplacer les caractères spéciaux par des underscores
        return nomFichier.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}

