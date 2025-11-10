package com.base.frame.achat.fonctionnelle.ressources;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.base.frame.achat.fonctionnelle.ressources;
//
//import com.base.frame.achat.fonctionnelle.services.TableauDeBordService;
//import com.base.frame.socle.utils.Constants;
//import java.util.ArrayList;
//import java.util.HashMap;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// *
// * @author Bouchara
// */
//@RestController
//@RequestMapping(path = "fonctionnelle/tableauDeBord")
//public class TableauDeBordRessource {
//
//    @Autowired
//    private TableauDeBordService tableauDeBordService;
//
//    @RequestMapping(value = "/getTableauDeBord/{choix}/{annee}", method = RequestMethod.GET)
//    public ResponseEntity<HashMap<String, Object>> getSMS(@PathVariable("choix") int choix, @PathVariable("annee") int annee) {
//        HashMap<String, Object> modeHashMap = new HashMap<>();
//        HttpHeaders headers = new HttpHeaders();
//        Long totalDeclaration;
//        Long totalDeclarationAvecSuite;
//        Long totalRequisition;
//        Long totalRequisitionAvecSuite;
//        Long totalSMS;
//        Long totalSMSLivre;
//        Long totalSMSLivrePourcentage;
//        Long totalSMSNonLivre;
//        Long totalSMSNonLivrePourcentage;
//        Long totalSMSEnAttente;
//        Long totalSMSEnAttentePourcentage;
//        Long totalSMSExpire;
//        Long totalSMSExpirePourcentage;
//        ArrayList<Long> donneDeclaration = new ArrayList<>(12);
//        ArrayList<Long> donneRequisition = new ArrayList<>(12);
//        ArrayList<Long> donneRequisitionDeclaration = new ArrayList<>(12);
//
//        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
//        System.out.println("choix:" + choix);
//        switch (choix) {
//            case 1:
//                totalDeclaration = this.tableauDeBordService.nombreTotaleDeclaration();
//                totalRequisition = this.tableauDeBordService.nombreTotaleRequisition();
//                totalDeclarationAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisition();
//                totalRequisitionAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisition();
//                totalSMS = this.tableauDeBordService.nombreSMSTotal();
//                if (totalSMS == 0) {
//                    totalSMSLivre = this.tableauDeBordService.nombreSMSLivre();
//                    totalSMSLivrePourcentage = Long.getLong("0");
//                    totalSMSNonLivre = this.tableauDeBordService.nombreSMSNonLivre();
//                    totalSMSNonLivrePourcentage = Long.getLong("0");
//                    totalSMSEnAttente = this.tableauDeBordService.nombreSMSEnAttente();
//                    totalSMSEnAttentePourcentage = Long.getLong("0");
//                    totalSMSExpire = this.tableauDeBordService.nombreSMSExpire();
//                    totalSMSExpirePourcentage = Long.getLong("0");
//                } else {
//                    totalSMSLivre = this.tableauDeBordService.nombreSMSLivre();
//                    totalSMSLivrePourcentage = (totalSMSLivre / totalSMS) * 100;
//                    totalSMSNonLivre = this.tableauDeBordService.nombreSMSNonLivre();
//                    totalSMSNonLivrePourcentage = (totalSMSNonLivre / totalSMS) * 100;
//                    totalSMSEnAttente = this.tableauDeBordService.nombreSMSEnAttente();
//                    totalSMSEnAttentePourcentage = (totalSMSEnAttente / totalSMS) * 100;
//                    totalSMSExpire = this.tableauDeBordService.nombreSMSExpire();
//                    totalSMSExpirePourcentage = (totalSMSExpire / totalSMS) * 100;
//                }
//
//                donneDeclaration = this.tableauDeBordService.nombreChiffreDeclaration(annee);
//                donneRequisition = this.tableauDeBordService.nombreChiffreRequisition(annee);
//                donneRequisitionDeclaration = this.tableauDeBordService.nombreChiffreRequisitionAvecDeclaration(annee);
//                break;
////            default:totalDeclaration = this.tableauDeBordService.nombreTotaleDeclaration();
////                    totalRequisition = this.tableauDeBordService.nombreTotaleRequisition();
////                    totalDeclarationAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisition();
////                    totalRequisitionAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisition();
////                    totalSMS = this.tableauDeBordService.nombreSMSTotal();
////                    totalSMSLivre = this.tableauDeBordService.nombreSMSLivre();
////                    totalSMSLivrePourcentage = (totalSMSLivre/totalSMS)*100;
////                    totalSMSNonLivre = this.tableauDeBordService.nombreSMSNonLivre();
////                    totalSMSNonLivrePourcentage = (totalSMSNonLivre/totalSMS)*100;
////                    totalSMSEnAttente = this.tableauDeBordService.nombreSMSEnAttente();
////                    totalSMSEnAttentePourcentage = (totalSMSEnAttente/totalSMS)*100;
////                    totalSMSExpire = this.tableauDeBordService.nombreSMSExpire();
////                    totalSMSExpirePourcentage = (totalSMSExpire/totalSMS)*100;
////                    donneDeclaration = this.tableauDeBordService.nombreChiffreDeclaration(annee);
////                    donneRequisition = this.tableauDeBordService.nombreChiffreRequisition(annee);
////                    donneRequisitionDeclaration = this.tableauDeBordService.nombreChiffreRequisitionAvecDeclaration(annee);
//            //}
//            case 2:
//                totalDeclaration = this.tableauDeBordService.nombreTotaleDeclarationMonService();
//                totalRequisition = this.tableauDeBordService.nombreTotaleRequisitionMonService();
//                totalDeclarationAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisitionMonServiceDeclaration();
//                totalRequisitionAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisitionMonServiceRequisition();
//                totalSMS = this.tableauDeBordService.nombreSMSTotalMonService();
//                if (totalSMS == 0) {
//                    totalSMSLivre = this.tableauDeBordService.nombreSMSLivreMonService();
//                    totalSMSLivrePourcentage = Long.getLong("0");
//                    totalSMSNonLivre = this.tableauDeBordService.nombreSMSNonLivreMonService();
//                    totalSMSNonLivrePourcentage = Long.getLong("0");
//                    totalSMSEnAttente = this.tableauDeBordService.nombreSMSEnAttenteMonService();
//                    totalSMSEnAttentePourcentage = Long.getLong("0");
//                    totalSMSExpire = this.tableauDeBordService.nombreSMSExpireMonService();
//                    totalSMSExpirePourcentage = Long.getLong("0");
//                } else {
//                    totalSMSLivre = this.tableauDeBordService.nombreSMSLivreMonService();
//                    totalSMSLivrePourcentage = (totalSMSLivre / totalSMS) * 100;
//                    totalSMSNonLivre = this.tableauDeBordService.nombreSMSNonLivreMonService();
//                    totalSMSNonLivrePourcentage = (totalSMSNonLivre / totalSMS) * 100;
//                    totalSMSEnAttente = this.tableauDeBordService.nombreSMSEnAttenteMonService();
//                    totalSMSEnAttentePourcentage = (totalSMSEnAttente / totalSMS) * 100;
//                    totalSMSExpire = this.tableauDeBordService.nombreSMSExpireMonService();
//                    totalSMSExpirePourcentage = (totalSMSExpire / totalSMS) * 100;
//                }
//
//                donneDeclaration = this.tableauDeBordService.nombreChiffreDeclarationMonService(annee);
//                donneRequisition = this.tableauDeBordService.nombreChiffreRequisitionMonService(annee);
//                donneRequisitionDeclaration = this.tableauDeBordService.nombreChiffreRequisitionAvecDeclarationMonService(annee);
//                break;
//            default:
//                totalDeclaration = this.tableauDeBordService.nombreTotaleDeclarationAutreService();
//                totalRequisition = this.tableauDeBordService.nombreTotaleRequisitionAutreService();
//                totalDeclarationAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisitionAutreServiceDeclaration();
//                totalRequisitionAvecSuite = this.tableauDeBordService.nombreSMSWithDeclarationAndRequisitionAutreServiceRequisition();
//                totalSMS = this.tableauDeBordService.nombreSMSTotalAutreService();
//                if (totalSMS == 0) {
//                    totalSMSLivre = this.tableauDeBordService.nombreSMSLivreAutreService();
//                    totalSMSLivrePourcentage =   new Long(0);
//                    totalSMSNonLivre = this.tableauDeBordService.nombreSMSNonLivreAutreService();
//                    totalSMSNonLivrePourcentage =  new Long(0);
//                    totalSMSEnAttente = this.tableauDeBordService.nombreSMSEnAttenteAutreService();
//                    totalSMSEnAttentePourcentage =   new Long(0);
//                    totalSMSExpire = this.tableauDeBordService.nombreSMSExpireAutreService();
//                    totalSMSExpirePourcentage =  new Long(0);
//                    System.out.println("totalSMSExpirePourcentage:"+totalSMSExpirePourcentage);
//                } else {
//                    totalSMSLivre = this.tableauDeBordService.nombreSMSLivreAutreService();
//                    totalSMSLivrePourcentage = (totalSMSLivre / totalSMS) * 100;
//                    totalSMSNonLivre = this.tableauDeBordService.nombreSMSNonLivreAutreService();
//                    totalSMSNonLivrePourcentage = (totalSMSNonLivre / totalSMS) * 100;
//                    totalSMSEnAttente = this.tableauDeBordService.nombreSMSEnAttenteAutreService();
//                    totalSMSEnAttentePourcentage = (totalSMSEnAttente / totalSMS) * 100;
//                    totalSMSExpire = this.tableauDeBordService.nombreSMSExpireAutreService();
//                    totalSMSExpirePourcentage = (totalSMSExpire / totalSMS) * 100;
//                }
//
//                donneDeclaration = this.tableauDeBordService.nombreChiffreDeclarationAutreService(annee);
//                donneRequisition = this.tableauDeBordService.nombreChiffreRequisitionAutreService(annee);
//                donneRequisitionDeclaration = this.tableauDeBordService.nombreChiffreRequisitionAvecDeclarationAutreService(annee);
//                break;
//        }
//
//        modeHashMap.put("totalDeclaration", totalDeclaration);
//        modeHashMap.put("totalRequisition", totalRequisition);
//        modeHashMap.put("totalDeclarationAvecSuite", totalDeclarationAvecSuite);
//        modeHashMap.put("totalRequisitionAvecSuite", totalRequisitionAvecSuite);
//        modeHashMap.put("totalSMS", totalSMS);
//        modeHashMap.put("totalSMSLivre", totalSMSLivre);
//        modeHashMap.put("totalSMSLivrePourcentage", totalSMSLivrePourcentage);
//        modeHashMap.put("totalSMSNonLivre", totalSMSNonLivre);
//        modeHashMap.put("totalSMSNonLivrePourcentage", totalSMSNonLivrePourcentage);
//        modeHashMap.put("totalSMSEnAttente", totalSMSEnAttente);
//        modeHashMap.put("totalSMSEnAttentePourcentage", totalSMSEnAttentePourcentage);
//        modeHashMap.put("totalSMSExpire", totalSMSExpire);
//        modeHashMap.put("totalSMSExpirePourcentage", totalSMSExpirePourcentage);
//        modeHashMap.put("donneDeclaration", donneDeclaration);
//        modeHashMap.put("donneRequisition", donneRequisition);
//        modeHashMap.put("donneRequisitionDeclaration", donneRequisitionDeclaration);
//
//        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
//    }
//
//}
