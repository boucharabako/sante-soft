package com.base.frame.achat.fonctionnelle.services;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.base.frame.achat.fonctionnelle.services;
//
//import com.base.frame.account.iservice.IUtilisateurService;
//import com.base.frame.achat.fonctionnelle.repositories.DeclarationRepository;
//import com.base.frame.achat.fonctionnelle.repositories.RequisitionRepository;
//import com.base.frame.achat.fonctionnelle.repositories.SMSRepository;
//import com.base.frame.achat.services.entities.Agent;
//import com.base.frame.achat.services.repositories.AgentRepository;
//import com.base.frame.achat.services.utils.AchatConstant;
//import com.base.frame.socle.core.ISecurityUtils;
//import com.base.frame.socle.core.utils.SprUtils;
//import java.time.Instant;
//import static java.time.Year.isLeap;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// *
// * @author Bouchara
// */
//@Service
//@Transactional
//public class TableauDeBordService {
//
//    @Autowired
//    private DeclarationRepository declarationRepository;
//    @Autowired
//    private RequisitionRepository requisitionRepository;
//    @Autowired
//    private SMSRepository smsRepository;
//    @Autowired
//    private ISecurityUtils securityUtils;
//    @Autowired
//    private IUtilisateurService utilisateurService;
//    @Autowired
//    private AgentRepository agentRepository;
//
//    @Transactional
//    public Long nombreTotaleDeclaration() {
//
//        return declarationRepository.count();
//    }
//    
//    @Transactional
//    public Long nombreTotaleDeclarationMonService() {
//
//        return declarationRepository.findDeclarationTotalService(this.getMonService());
//    }
//    @Transactional
//    public Long nombreTotaleDeclarationAutreService() {
//        
//        return declarationRepository.findDeclarationTotalAutreService(this.getMonService());
//    }
//
//    @Transactional
//    public Long nombreTotaleRequisition() {
//
//        return requisitionRepository.count();
//    }
//
//    @Transactional
//    public Long nombreTotaleRequisitionMonService() {
//
//        return requisitionRepository.findRequisitionTotalService(this.getMonService());
//    }
//    @Transactional
//    public Long nombreTotaleRequisitionAutreService() {
//        
//        return requisitionRepository.findRequisitionTotalAutreService(this.getMonService());
//    }
//    
//    @Transactional
//    public Long nombreSMSWithDeclarationAndRequisition() {
//
//        return smsRepository.findNombreSMSWithDeclarationAndRequisition();
//    }
//    
//    @Transactional
//    public Long nombreSMSWithDeclarationAndRequisitionMonServiceRequisition() {
//
//        return smsRepository.findNombreSMSWithDeclarationAndRequisitionMonServiceRequisition(this.getMonService());
//    }
//    
//    @Transactional
//    public Long nombreSMSWithDeclarationAndRequisitionMonServiceDeclaration() {
//
//        return smsRepository.findNombreSMSWithDeclarationAndRequisitionMonServiceDeclaration(this.getMonService());
//    }
//    
//    @Transactional
//    public Long nombreSMSWithDeclarationAndRequisitionAutreServiceRequisition() {
//
//        return smsRepository.findNombreSMSWithDeclarationAndRequisitionAutreServiceRequisition(this.getMonService());
//    }
//    
//    @Transactional
//    public Long nombreSMSWithDeclarationAndRequisitionAutreServiceDeclaration() {
//
//        return smsRepository.findNombreSMSWithDeclarationAndRequisitionAutreServiceDeclaration(this.getMonService());
//    }
//
//    @Transactional
//    public Long nombreSMSLivre() {
//
//        return smsRepository.findNombreSMSByStatut(AchatConstant.ETAT_SMS_LIVRE);
//    }
//    
//    @Transactional
//    public Long nombreSMSLivreMonService() {
//
//        return smsRepository.findNombreSMSByStatutMonService(AchatConstant.ETAT_SMS_LIVRE,this.getMonService());
//    }
//    
//    @Transactional
//    public Long nombreSMSLivreAutreService() {
//
//        return smsRepository.findNombreSMSByStatutAutreService(AchatConstant.ETAT_SMS_LIVRE,this.getMonService());
//    }
//
//    @Transactional
//    public Long nombreSMSNonLivre() {
//
//        return smsRepository.findNombreSMSByStatut(AchatConstant.ETAT_SMS_NON_LIVRE);
//    }
//    
//    @Transactional
//    public Long nombreSMSNonLivreMonService() {
//
//        return smsRepository.findNombreSMSByStatutMonService(AchatConstant.ETAT_SMS_NON_LIVRE,this.getMonService());
//    }
//    @Transactional
//    public Long nombreSMSNonLivreAutreService() {
//
//        return smsRepository.findNombreSMSByStatutAutreService(AchatConstant.ETAT_SMS_NON_LIVRE,this.getMonService());
//    }
//
//    @Transactional
//    public Long nombreSMSEnAttente() {
//
//        return smsRepository.findNombreSMSByStatut(AchatConstant.ETAT_SMS_EN_ATTENTE);
//    }
//    @Transactional
//    public Long nombreSMSEnAttenteMonService() {
//
//        return smsRepository.findNombreSMSByStatutMonService(AchatConstant.ETAT_SMS_EN_ATTENTE,this.getMonService());
//    }
//    @Transactional
//    public Long nombreSMSEnAttenteAutreService() {
//
//        return smsRepository.findNombreSMSByStatutAutreService(AchatConstant.ETAT_SMS_EN_ATTENTE,this.getMonService());
//    }
//
//    @Transactional
//    public Long nombreSMSExpire() {
//
//        return smsRepository.findNombreSMSByStatut(AchatConstant.ETAT_SMS_EXPIRE);
//    }
//    @Transactional
//    public Long nombreSMSExpireMonService() {
//
//        return smsRepository.findNombreSMSByStatutMonService(AchatConstant.ETAT_SMS_EXPIRE,this.getMonService());
//    }
//    @Transactional
//    public Long nombreSMSExpireAutreService() {
//
//        return smsRepository.findNombreSMSByStatutAutreService(AchatConstant.ETAT_SMS_EXPIRE,this.getMonService());
//    }
//
//    @Transactional
//    public Long nombreSMSTotal() {
//        System.out.println("smsRepository.findNombreTotalSMS():"+smsRepository.findNombreTotalSMS());
//        return smsRepository.findNombreTotalSMS();
//    }
//    
//    @Transactional
//    public Long nombreSMSTotalMonService() {
//
//        return smsRepository.findNombreTotalSMSMonService(this.getMonService());
//    }
//    @Transactional
//    public Long nombreSMSTotalAutreService() {
//
//        return smsRepository.findNombreTotalSMSAutreService(this.getMonService());
//    }
//    
//    
//
//    @Transactional
//    public ArrayList<Long> nombreChiffreDeclaration(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(declarationRepository.findDeclarationEntreDateDebutDateFin(dateDebut,dateFin));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(declarationRepository.findDeclarationEntreDateDebutDateFin(dateDebut,dateFin));
//        }
//
//        return chiffresParMois;
//    }
//    @Transactional
//    public ArrayList<Long> nombreChiffreDeclarationMonService(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(declarationRepository.findDeclarationEntreDateDebutDateFinMonService(dateDebut,dateFin,this.getMonService()));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(declarationRepository.findDeclarationEntreDateDebutDateFinMonService(dateDebut,dateFin,this.getMonService()));
//        }
//
//        return chiffresParMois;
//    }
//    @Transactional
//    public ArrayList<Long> nombreChiffreDeclarationAutreService(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(declarationRepository.findDeclarationEntreDateDebutDateFinAutreService(dateDebut,dateFin,this.getMonService()));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(declarationRepository.findDeclarationEntreDateDebutDateFinAutreService(dateDebut,dateFin,this.getMonService()));
//        }
//
//        return chiffresParMois;
//    }
//    @Transactional
//    public ArrayList<Long> nombreChiffreRequisition(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(requisitionRepository.findRequisitionEntreDateDebutDateFin(dateDebut,dateFin));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(requisitionRepository.findRequisitionEntreDateDebutDateFin(dateDebut,dateFin));
//        }
//
//        return chiffresParMois;
//    }
//    @Transactional
//    public ArrayList<Long> nombreChiffreRequisitionMonService(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(requisitionRepository.findRequisitionEntreDateDebutDateFinMonService(dateDebut,dateFin,this.getMonService()));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(requisitionRepository.findRequisitionEntreDateDebutDateFinMonService(dateDebut,dateFin,this.getMonService()));
//        }
//
//        return chiffresParMois;
//    }
//    @Transactional
//    public ArrayList<Long> nombreChiffreRequisitionAutreService(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(requisitionRepository.findRequisitionEntreDateDebutDateFinAutreService(dateDebut,dateFin,this.getMonService()));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(requisitionRepository.findRequisitionEntreDateDebutDateFinAutreService(dateDebut,dateFin,this.getMonService()));
//        }
//
//        return chiffresParMois;
//    }
//    
//    @Transactional
//    public ArrayList<Long> nombreChiffreRequisitionAvecDeclaration(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(smsRepository.findNombreSMSWithDeclarationAndRequisitionEntreDateDebutDateFin(dateDebut,dateFin));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(smsRepository.findNombreSMSWithDeclarationAndRequisitionEntreDateDebutDateFin(dateDebut,dateFin));
//        }
//
//        return chiffresParMois;
//    }
//    @Transactional
//    public ArrayList<Long> nombreChiffreRequisitionAvecDeclarationMonService(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(smsRepository.findNombreSMSWithDeclarationAndRequisitionEntreDateDebutDateFinMonService(dateDebut,dateFin,this.getMonService()));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(smsRepository.findNombreSMSWithDeclarationAndRequisitionEntreDateDebutDateFinMonService(dateDebut,dateFin,this.getMonService()));
//        }
//
//        return chiffresParMois;
//    }
//    @Transactional
//    public ArrayList<Long> nombreChiffreRequisitionAvecDeclarationAutreService(int annee) {
//        Instant dateDebut;
//        Instant dateFin;
//        ArrayList<Long> chiffresParMois = new ArrayList<>(12);
//        for (int i = 1; i <= 9; i++) {
//            String dateD = annee + "-0" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-0" + i + "-"+getNombreJour(i, annee)+"T23:00:00.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(smsRepository.findNombreSMSWithDeclarationAndRequisitionEntreDateDebutDateFinAutreService(dateDebut,dateFin,this.getMonService()));
//        }
//        for (int i = 10; i <= 12; i++) {
//            String dateD = annee + "-" + i + "-01T00:00:00.000Z";
//            String dateF = annee + "-" + i + "-"+getNombreJour(i, annee)+"T23:59:59.000Z";
//            System.out.println("dateD:"+dateD);
//            System.out.println("dateF:"+dateF);
//            dateDebut = SprUtils.getDateToUTC(Instant.parse(dateD));
//            dateFin = SprUtils.getDateToUTC(Instant.parse(dateF));
//            chiffresParMois.add(smsRepository.findNombreSMSWithDeclarationAndRequisitionEntreDateDebutDateFinAutreService(dateDebut,dateFin,this.getMonService()));
//        }
//
//        return chiffresParMois;
//    }
//    
//    
//    private String getMonService(){
//        Optional<String> username = securityUtils.getCurrentUserLogin();
//        if (username.isPresent() && this.utilisateurService.findUtilisateurUsername(username.get()).isPresent()) {
//            if (this.utilisateurService.findUtilisateurUsername(username.get()).get().getClass().equals(Agent.class)) {
//                  Agent  agent = agentRepository.getOne(this.utilisateurService.findUtilisateurUsername(username.get()).get().getId());
//                  return agent.getService().getId();
//              }      
//        }
//        return "";
//    }
//    private int getNombreJour(int mois, int annee) {
//         final int nombredejours;
//        switch (mois) {
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 12:
//          nombredejours = 31;
//                break;
//            case 2:
//                if (isLeap(annee)) {
//              nombredejours = 29;
//                } else {
//              nombredejours = 28;
//                }
//                break;
//            default:
//                nombredejours = 30;
//        }
//        
//        return nombredejours;
//    }
//
//}
