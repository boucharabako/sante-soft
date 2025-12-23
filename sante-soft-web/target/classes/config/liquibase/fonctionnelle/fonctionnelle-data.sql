/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Bouchara
 * Created: 23 Juillet 2024
 */

INSERT INTO achat.categorie_produit(id, code_categorie, libelle_categorie, description_categorie) 
VALUES 
('1','MED', 'Medicaments pharmaceutiques','Medicaments pharmaceutiques')

ON CONFLICT (id) DO UPDATE 
SET
code_categorie= EXCLUDED.code_categorie,
libelle_categorie= EXCLUDED.libelle_categorie,
description_categorie= EXCLUDED.description_categorie;


INSERT INTO achat.type_produit(id, code_type_produit, libelle_type_produit, description_type_produit,categorie_produit) 
VALUES 
('1','ANTBIO', 'Antibiotiques','Antibiotiques','1'),
('2','ANINF', 'Analgésiques et Anti-inflammatoires','Analgésiques et Anti-inflammatoires','1'),
('3','AB', 'Antibactériens','Antibactériens','1'),
('4','AND', 'antidouleurs','antidouleurs','1'),
('5','ANTPAR', 'antiparasitaires','antiparasitaires','1'),
('6','ANTPAL', 'antipaludéen','antipaludéen','1')


ON CONFLICT (id) DO UPDATE 
SET
code_type_produit= EXCLUDED.code_type_produit,
libelle_type_produit= EXCLUDED.libelle_type_produit,
description_type_produit= EXCLUDED.description_type_produit,
categorie_produit= EXCLUDED.categorie_produit;


-- INSERT INTO achat.produit(id, code_produit, libelle_produit, description_produit, type_produit) 
-- VALUES 
-- ('1','AMX', 'Amoxicilline','amoxicilline','1'),
-- ('2','AZX', 'Azithromycine','azithromycine','1'),
-- ('3','FOX', 'Fosfomycine','fosfomycine','1'),
-- ('4','PAR', 'Paracétamol','Paracétamol','2'),
-- ('5','IBF', 'Ibuprofène','Ibuprofène','2'),
-- ('6','AND', 'Albendazole ','Albendazole','5'),
-- ('7','AND', 'flubendazole ','flubendazole ','5')
-- 
-- ON CONFLICT (id) DO UPDATE 
-- SET
-- code_produit= EXCLUDED.code_produit,
-- libelle_produit= EXCLUDED.libelle_produit,
-- description_produit= EXCLUDED.description_produit,
-- type_produit= EXCLUDED.type_produit;




INSERT INTO  socle.socle_fonction(code,libelle,page_uri_base,api_uri_base,parent,type_fonction,niveau_habilitation_max)
VALUES
('FC_PROD','Gestion des produits','/gestion','/gestion/produit', 'FC_HABILIT','FONCTION','PARAM_NVX_SA'),
('FC_COMM','Gestion des commandes','/gestion','/gestion/commande', 'FC_HABILIT','FONCTION','PARAM_NVX_SA'),
('FC_PATIENT','Gestion des patients','/gestion','/gestion/patient', 'FC_HABILIT','FONCTION','PARAM_NVX_SA'),
('FC_PROFESSIONNEL','Gestion des professionnels de santé','/gestion','/gestion/professionnel', 'FC_HABILIT','FONCTION','PARAM_NVX_SA'),
('FC_ETABLISSEMENT','Gestion des établissements','/gestion','/gestion/etablissement', 'FC_HABILIT','FONCTION','PARAM_NVX_SA'),
('FC_CONS_LIST','Liste des consultations','/gestion','/gestion/consultation/liste', 'FC_HABILIT','FONCTION','PARAM_NVX_SA'),
('FC_INF_PATIENT','Informations sur patient','/gestion','/gestion/information/patient', 'FC_HABILIT','FONCTION','PARAM_NVX_SA')

ON CONFLICT (code) DO UPDATE
SET
libelle= EXCLUDED.libelle,
page_uri_base =EXCLUDED.page_uri_base,
api_uri_base =EXCLUDED.api_uri_base,
parent =EXCLUDED.parent,
type_fonction =EXCLUDED.type_fonction,
niveau_habilitation_max =EXCLUDED.niveau_habilitation_max;




INSERT INTO sante.categorie_antecedent(id, code, libelle, description) 
VALUES 
('1','AME','Antécédents médicaux','Antécédents médicaux'),
('2','ACH','Antécédents chirurgicaux','Antécédents chirurgicaux'),
('3','AFA','Antécédents familiaux','Antécédents familiaux'),
('4','AAL','Antécédents allergiques','Antécédents allergiques'),
('5','AOG','Antécédents obstétricaux et gynécologiques','Antécédents obstétricaux et gynécologiques (concernent exclusivement les femmes)'),
('6','ATR','Antécédents traumatiques','Antécédents traumatiques'),
('7','AMD','Antécédents médicamenteux','Antécédents médicamenteux (ex. traitements au long cours)'),
('8','ASO','Antécédents sociaux','Antécédents sociaux (tabac, alcool, cadre de vie)')

ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
libelle= EXCLUDED.libelle,
description= EXCLUDED.description;


INSERT INTO sante.type_antecedent(id, code, categorie_antecedent, libelle, description) 
VALUES 

-- Antécédents médicaux (AME)
('1', 'MCR', '1', 'Maladies chroniques', 'Maladies chroniques'), 
('2', 'MIN', '1', 'Maladies infectieuses', 'Maladies infectieuses'),
('3', 'MME', '1', 'Maladies métaboliques', 'Maladies métaboliques'),
('4', 'MCV', '1', 'Maladies cardiovasculaires', 'Maladies cardiovasculaires'),
('5', 'MRE', '1', 'Maladies respiratoires', 'Maladies respiratoires'),
('6', 'MNE', '1', 'Maladies neurologiques', 'Maladies neurologiques'),

-- Antécédents chirurgicaux (ACH)
('7', 'CDI', '2', 'Chirurgie digestive', 'Chirurgie digestive'),
('8', 'CCV', '2', 'Chirurgie cardiovasculaire', 'Chirurgie cardiovasculaire'),
('9', 'COR', '2', 'Chirurgie ORL', 'Chirurgie ORL'),
('10', 'COB', '2', 'Chirurgie obstétrique', 'Chirurgie obstétrique'),
('11', 'COT', '2', 'Chirurgie orthopédique', 'Chirurgie orthopédique'),

-- Antécédents familiaux (AFA)
('16', 'MHF', '3', 'Maladies héréditaires', 'Maladies héréditaires'),
('17', 'MCF', '3', 'Maladies cardiovasculaires familiales', 'Maladies cardiovasculaires familiales'),
('18', 'CAF', '3', 'Cancers familiaux', 'Cancers familiaux'),
('19', 'DIF', '3', 'Diabète familial', 'Diabète familial'),


-- Antécédents allergiques (AAL)
('12', 'ALM', '4', 'Allergie médicamenteuse', 'Allergie médicamenteuse'),
('13', 'ALA', '4', 'Allergie alimentaire', 'Allergie alimentaire'),
('14', 'ALR', '4', 'Allergie respiratoire', 'Allergie respiratoire'),
('15', 'ALC', '4', 'Allergie de contact', 'Allergie de contact'),


-- Antécédents obstétricaux / gynécologiques (AOG)
('20', 'GRP', '5', 'Grossesses précédentes', 'Grossesses précédentes'),
('21', 'ACC', '5', 'Accouchements antérieurs', 'Accouchements antérieurs'),
('22', 'COP', '5', 'Complications obstétricales', 'Complications obstétricales')

ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
categorie_antecedent= EXCLUDED.categorie_antecedent,
libelle= EXCLUDED.libelle,
description= EXCLUDED.description;


INSERT INTO sante.type_observation (id, libelle, description, unite, valeur_min, valeur_max)
VALUES
('1', 'Température', 'Température corporelle mesurée avec un thermomètre', '°C', '30.0', '43.0'),
('2', 'Poids', 'Poids du patient mesuré sur une balance médicale', 'kg', '1.0', '350.0'),
('3', 'Taille', 'Taille du patient mesurée debout', 'cm', '40', '280'),
('4', 'Pression artérielle systolique', 'Pression artérielle systolique (tension haute)', 'mmHg', '60', '260'),
('5', 'Pression artérielle diastolique', 'Pression artérielle diastolique (tension basse)', 'mmHg', '30', '160')

ON CONFLICT (id) DO UPDATE 
SET
libelle= EXCLUDED.libelle,
description= EXCLUDED.description,
unite= EXCLUDED.unite,
valeur_min= EXCLUDED.valeur_min,
valeur_max= EXCLUDED.valeur_max;



INSERT INTO sante.antecedent(id, code, type_antecedent, libelle, description) 
VALUES 

-- ============================
-- 1. MALADIES CHRONIQUES (1)
-- ============================
('1',  'HTA',   '1', 'Hypertension artérielle', 'Hypertension artérielle chronique'),
('2',  'DT2',   '1', 'Diabète de type 2', 'Diabète sucré de type 2'),
('3',  'IRC',   '1', 'Insuffisance rénale chronique', 'Atteinte rénale chronique'),
('4',  'ASTH',  '1', 'Asthme chronique', 'Asthme persistant'),
('5', 'ARTH',  '1', 'Arthrose', 'Maladie dégénérative des articulations'),
('6', 'RCH',   '1', 'Rectocolite hémorragique', 'Maladie inflammatoire chronique intestinale'),
('7', 'CROHN', '1', 'Maladie de Crohn', 'Atteinte inflammatoire du tube digestif'),

-- ============================
-- 2. MALADIES INFECTIEUSES (2)
-- ============================
('8',  'TB',   '2', 'Tuberculose', 'Tuberculose pulmonaire ou extrapulmonaire'),
('9',  'VIH',  '2', 'VIH', 'Infection par le virus VIH'),
('10',  'VHB',  '2', 'Hépatite B', 'Infection chronique ou aiguë par le VHB'),
('11', 'VHC',  '2', 'Hépatite C', 'Infection par le virus de l’hépatite C'),
('12', 'PALU', '2', 'Paludisme', 'Épisodes de paludisme confirmés'),

-- ============================
-- 3. MALADIES MÉTABOLIQUES (3)
-- ============================
('13',  'DLP',  '3', 'Dyslipidémie', 'Anomalie du cholestérol ou des triglycérides'),
('14',  'GOUT', '3', 'Goutte', 'Hyperuricémie chronique'),
('15', 'OBES', '3', 'Obésité', 'IMC ≥ 30 kg/m²'),
('16', 'HYPTH','3', 'Hypothyroïdie', 'Déficit en hormones thyroïdiennes'),

-- ============================
-- 4. MALADIES CARDIOVASCULAIRES (4)
-- ============================
('17', 'IDM',  '4', 'Infarctus du myocarde', 'Antécédent d’IDM'),
('18', 'AVC',  '4', 'AVC', 'Accident vasculaire cérébral'),
('19', 'IC',   '4', 'Insuffisance cardiaque', 'Insuffisance cardiaque chronique'),
('20', 'ARTP', '4', 'Artériopathie périphérique', 'Atteinte des artères des membres inférieurs'),

-- ============================
-- 5. MALADIES RESPIRATOIRES (5)
-- ============================
('21', 'BPCO',  '5', 'BPCO', 'Bronchopneumopathie chronique obstructive'),
('22', 'PNM-R','5', 'Pneumonie récidivante', 'Pneumonies répétées'),
('23', 'FIBP', '5', 'Fibrose pulmonaire', 'Maladie pulmonaire interstitielle'),

-- ============================
-- 6. MALADIES NEUROLOGIQUES (6)
-- ============================
('24', 'EPI',  '6', 'Épilepsie', 'Crises épileptiques récurrentes'),
('25', 'MIGC', '6', 'Migraine chronique', 'Migraine sévère persistante'),
('26', 'PARK', '6', 'Maladie de Parkinson', 'Trouble neurodégénératif'),
('27', 'DEM',  '6', 'Démence', 'Déclin cognitif progressif'),

-- ============================
-- 7. CHIRURGIE DIGESTIVE (7)
-- ============================
('28', 'APPE', '7', 'Appendicectomie', 'Ablation de l’appendice'),
('29', 'CHOLC','7', 'Cholécystectomie', 'Ablation de la vésicule biliaire'),
('30', 'HERNV','7', 'Hernie inguinale opérée', 'Cure de hernie inguinale'),

-- ============================
-- 8. CHIRURGIE CARDIOVASCULAIRE (8)
-- ============================
('31', 'PONT', '8', 'Pontage coronarien', 'Revascularisation'),
('32', 'STENT','8', 'Pose de stent', 'Stent coronarien'),
('33', 'PACEM','8', 'Pacemaker', 'Implantation d’un stimulateur cardiaque'),

-- ============================
-- 9. CHIRURGIE ORL (9)
-- ============================
('34', 'AMYG', '9', 'Amygdalectomie', 'Ablation des amygdales'),
('35', 'ADENO','9', 'Adénoïdectomie', 'Ablation des végétations'),

-- ============================
-- 10. CHIRURGIE OBSTÉTRIQUE (10)
-- ============================
('36', 'CESA','10', 'Césarienne', 'Accouchement par césarienne'),
('37', 'GEU', '10', 'Grossesse extra-utérine opérée', 'Traitement chirurgical de GEU'),

-- ============================
-- 11. CHIRURGIE ORTHOPÉDIQUE (11)
-- ============================
('38', 'FRACT','11', 'Fracture opérée', 'Ostéosynthèse'),
('39', 'PROTH', '11', 'Prothèse articulaire', 'Prothèse de hanche ou de genou'),

-- ============================
-- 12–15. ALLERGIES (12–15)
-- ============================
('40', 'AL-PEN','12', 'Allergie à la pénicilline', 'Réaction allergique aux pénicillines'),
('41', 'AL-AR', '13', 'Allergie à l’arachide', 'Hypersensibilité alimentaire'),
('42', 'ASTH-A','14', 'Asthme allergique', 'Allergie respiratoire avec asthme'),
('43', 'AL-NI', '15', 'Allergie au nickel', 'Dermatite de contact'),
('44', 'AL-LAT','12', 'Allergie au latex', 'Réaction au latex médical'),

-- ============================
-- 16–19. ANTÉCÉDENTS FAMILIAUX
-- ============================
('45', 'DFAM', '16', 'Drépanocytose familiale', 'Hérédité drépanocytaire'),
('46', 'IDM-F','17', 'Infarctus familial', 'Antécédent d’IDM chez un parent proche'),
('47', 'CS-F', '18', 'Cancer du sein familial', 'Cancer du sein dans la famille'),
('48', 'DBT-F','19', 'Diabète familial', 'Diabète chez un parent proche'),
('49', 'HTA-F','17', 'Hypertension familiale', 'HTA chez un parent de premier degré'),

-- ============================
-- 20–22. ANTÉCÉDENTS OBSTÉTRICAUX (20–22)
-- ============================
('50', 'GEM',  '20', 'Grossesse gémellaire antérieure', 'Antécédent de grossesse multiple'),
('51', 'PREM', '21', 'Accouchement prématuré', 'Avant 37 SA'),
('52', 'PREE', '22', 'Prééclampsie', 'Complication obstétricale sévère'),
('53', 'FCS',  '21', 'Fausses couches répétées', '≥ 2 fausses couches spontanées consécutives')

ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
type_antecedent= EXCLUDED.type_antecedent,
libelle= EXCLUDED.libelle,
description= EXCLUDED.description;

-- CATEGORIE CONSULTATION 

INSERT INTO sante.categorie_consultation(id, code, libelle, description) 
VALUES
('1', 'CMG', 'Consultation médicale générale', 'Consultation médicale de premier niveau'),
('2', 'CSP', 'Consultation spécialisée', 'Consultation réalisée par un spécialiste'),
('3', 'CUR', 'Consultation d’urgence', 'Consultation en situation d’urgence médicale'),
('4', 'CSU', 'Consultation de suivi', 'Consultation de contrôle ou de suivi thérapeutique'),
('5', 'CPV', 'Consultation préventive', 'Consultation de prévention et de dépistage'),
('6', 'CPN', 'Consultation prénatale et maternelle', 'Suivi de la grossesse et de la maternité'),
('7', 'CPD', 'Consultation pédiatrique', 'Consultation médicale destinée aux enfants'),
('8', 'CCH', 'Consultation chirurgicale', 'Consultation liée à un acte chirurgical'),
('9', 'CPM', 'Consultation paramédicale', 'Consultation réalisée par un personnel paramédical'),
('10','CRF', 'Consultation de référence', 'Consultation de référence ou contre-référence'),
('11','CTM', 'Téléconsultation', 'Consultation médicale à distance'),
('12','CAD', 'Consultation administrative', 'Consultation à but administratif ou médico-légal')

ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
libelle= EXCLUDED.libelle,
description= EXCLUDED.description;


INSERT INTO sante.type_consultation(id, code, categorie_consultation, libelle, description) 
VALUES 

-- Consultation médicale générale (CMG = 1)
('1', 'CMG-INI', '1', 'Consultation initiale', 'Première consultation du patient'),
('2', 'CMG-SYM', '1', 'Consultation symptomatique', 'Consultation pour symptômes généraux'),

-- Consultation spécialisée (CSP = 2)
('3', 'CSP-CAR', '2', 'Consultation cardiologique', 'Consultation spécialisée en cardiologie'),
('4', 'CSP-END', '2', 'Consultation endocrinologique', 'Consultation spécialisée en endocrinologie'),
('5', 'CSP-NEU', '2', 'Consultation neurologique', 'Consultation spécialisée en neurologie'),
('6', 'CSP-GYN', '2', 'Consultation gynécologique', 'Consultation spécialisée en gynécologie'),

-- Consultation d’urgence (CUR = 3)
('7', 'CUR-VIT', '3', 'Urgence vitale', 'Consultation pour urgence vitale immédiate'),
('8', 'CUR-MED', '3', 'Urgence médicale', 'Consultation pour urgence médicale non chirurgicale'),
('9', 'CUR-TRA', '3', 'Urgence traumatique', 'Consultation suite à un traumatisme'),

-- Consultation de suivi (CSU = 4)
('10', 'CSU-CHN', '4', 'Suivi maladie chronique', 'Consultation de suivi d’une pathologie chronique'),
('11', 'CSU-TRT', '4', 'Suivi thérapeutique', 'Évaluation de l’efficacité du traitement'),

-- Consultation préventive (CPV = 5)
('12', 'CPV-DPT', '5', 'Consultation de dépistage', 'Consultation de dépistage précoce'),
('13', 'CPV-VAC', '5', 'Consultation vaccinale', 'Consultation pour vaccination'),

-- Consultation prénatale et maternelle (CPN = 6)
('14', 'CPN-SUI', '6', 'Suivi prénatal', 'Consultation de suivi de grossesse'),
('15', 'CPN-POST', '6', 'Consultation post-natale', 'Suivi médical après accouchement'),

-- Consultation pédiatrique (CPD = 7)
('16', 'CPD-SUI', '7', 'Suivi pédiatrique', 'Consultation de suivi de l’enfant'),
('17', 'CPD-MAL', '7', 'Consultation maladie infantile', 'Consultation pour pathologie chez l’enfant'),

-- Consultation chirurgicale (CCH = 8)
('18', 'CCH-PRE', '8', 'Consultation préopératoire', 'Évaluation avant intervention chirurgicale'),
('19', 'CCH-POST','8', 'Consultation post-opératoire', 'Suivi après intervention chirurgicale'),

-- Consultation paramédicale (CPM = 9)
('20', 'CPM-INF', '9', 'Soins infirmiers', 'Consultation pour soins infirmiers'),
('21', 'CPM-KIN', '9', 'Rééducation fonctionnelle', 'Consultation de kinésithérapie'),

-- Consultation de référence (CRF = 10)
('22', 'CRF-REF', '10', 'Consultation de référence', 'Consultation suite à une référence médicale'),
('23', 'CRF-CTR', '10', 'Consultation de contre-référence', 'Retour d’information vers la structure d’origine'),

-- Téléconsultation (CTM = 11)
('24', 'CTM-GEN', '11', 'Téléconsultation générale', 'Consultation médicale à distance'),
('25', 'CTM-SUI', '11', 'Téléconsultation de suivi', 'Suivi médical à distance'),

-- Consultation administrative (CAD = 12)
('26', 'CAD-APT', '12', 'Visite d’aptitude', 'Consultation pour certificat d’aptitude'),
('27', 'CAD-LEG', '12', 'Consultation médico-légale', 'Consultation à but légal ou administratif')


ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
libelle= EXCLUDED.libelle,
categorie_consultation=EXCLUDED.categorie_consultation,
description= EXCLUDED.description;



INSERT INTO sante.consultation_type_examen_autorise(id, code, type_consultation, libelle, description)
VALUES

-- CMG-INI (1) : Consultation initiale
('1', 'EX-CLIN', '1', 'Examen clinique général', 'Examen clinique complet du patient'),
('2', 'TA',       '1', 'Tension artérielle', 'Mesure de la pression artérielle'),
('3', 'NFS',      '1', 'NFS', 'Numération formule sanguine'),
('4', 'GLY',      '1', 'Glycémie', 'Dosage de la glycémie'),
('5', 'ECBU',     '1', 'ECBU', 'Examen cytobactériologique des urines'),

-- CMG-SYM (2) : Consultation symptomatique
('6', 'CRP',      '2', 'CRP', 'Marqueur de l’inflammation'),
('7', 'TDR-PAL',  '2', 'Test paludisme', 'Test de diagnostic rapide du paludisme'),
('8', 'RAD-THX',  '2', 'Radiographie thoracique', 'Imagerie du thorax'),
('9', 'ECG',      '2', 'ECG', 'Électrocardiogramme'),

-- CSP-CAR (3) : Cardiologie
('10', 'ECG',     '3', 'ECG', 'Électrocardiogramme'),
('11', 'ECHO-C',  '3', 'Échocardiographie', 'Échographie cardiaque'),
('12', 'LIPID',   '3', 'Bilan lipidique', 'Cholestérol et triglycérides'),
('13', 'HOLTER',  '3', 'Holter ECG', 'Enregistrement ECG prolongé'),

-- CSP-END (4) : Endocrinologie
('14', 'HBA1C',   '4', 'HbA1c', 'Équilibre glycémique'),
('15', 'TSH',     '4', 'TSH', 'Fonction thyroïdienne'),
('16', 'T3T4',    '4', 'T3 / T4', 'Hormones thyroïdiennes'),

-- CSP-NEU (5) : Neurologie
('17', 'EEG',     '5', 'EEG', 'Électroencéphalogramme'),
('18', 'IRM-C',   '5', 'IRM cérébrale', 'Imagerie cérébrale'),
('19', 'SCAN-C',  '5', 'Scanner cérébral', 'Tomodensitométrie du cerveau'),

-- CSP-GYN (6) : Gynécologie
('20', 'FROT',    '6', 'Frottis cervico-vaginal', 'Dépistage du cancer du col'),
('21', 'ECHO-P',  '6', 'Échographie pelvienne', 'Imagerie gynécologique'),
('22', 'TEST-G',  '6', 'Test de grossesse', 'Dosage β-HCG'),

-- CUR-VIT (7) : Urgence vitale
('23', 'GAZ',     '7', 'Gaz du sang', 'Analyse respiratoire et métabolique'),
('24', 'ECG-U',   '7', 'ECG urgent', 'ECG en urgence'),
('25', 'SCAN-U',  '7', 'Scanner urgent', 'Imagerie d’urgence'),

-- CUR-TRA (9) : Urgence traumatique
('26', 'RAD-OS',  '9', 'Radiographie osseuse', 'Recherche de fracture'),
('27', 'FAST',    '9', 'Échographie FAST', 'Recherche d’hémorragie interne'),

-- CSU-CHN (10) : Suivi maladie chronique
('28', 'BIO-SUI', '10', 'Bilan de suivi', 'Surveillance biologique'),
('29', 'ECG-S',   '10', 'ECG de suivi', 'Surveillance cardiaque'),

-- CPV-DPT (12) : Dépistage
('30', 'VIH',     '12', 'Test VIH', 'Dépistage VIH'),
('31', 'VHB',     '12', 'Hépatite B', 'Dépistage hépatite B'),
('32', 'PSA',     '12', 'PSA', 'Dépistage prostate'),

-- CPN-SUI (14) : Suivi prénatal
('33', 'ECHO-OBS','14', 'Échographie obstétricale', 'Suivi de grossesse'),
('34', 'RAI',     '14', 'RAI', 'Recherche d’agglutinines irrégulières'),
('35', 'BU-G',    '14', 'Bandelette urinaire', 'Dépistage urinaire grossesse'),

-- CPD-MAL (17) : Maladie infantile
('36', 'NFS-P',   '17', 'NFS pédiatrique', 'Analyse sanguine enfant'),
('37', 'PAL-P',   '17', 'Test paludisme enfant', 'Dépistage paludisme pédiatrique'),

-- CCH-PRE (18) : Préopératoire
('38', 'BPO',     '18', 'Bilan préopératoire', 'Bilan biologique préchirurgical'),
('39', 'COAG',    '18', 'Bilan de coagulation', 'Risque hémorragique'),

-- CTM-GEN (24) : Téléconsultation
('40', 'ORD-BIO', '24', 'Prescription examens', 'Prescription d’examens à distance'),

-- CAD-APT (26) : Visite d’aptitude
('41', 'VIS',     '26', 'Test visuel', 'Contrôle de la vision'),
('42', 'AUD',     '26', 'Test auditif', 'Contrôle de l’audition')

ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
libelle= EXCLUDED.libelle,
type_consultation=EXCLUDED.type_consultation,
description= EXCLUDED.description;

INSERT INTO sante.vaccin(id, code, libelle, maladie_cible, obligatoire, description)
VALUES

-- Vaccinations de base (nourrisson / enfance)
('1', 'BCG', 'Vaccin BCG', 'Tuberculose', true, 'Vaccination obligatoire contre la tuberculose'),
('2', 'DTC', 'Vaccin Diphtérie-Tétanos-Coqueluche', 'Diphtérie, Tétanos, Coqueluche', true, 'Vaccin combiné obligatoire dès la petite enfance'),
('3', 'POLIO', 'Vaccin antipoliomyélitique', 'Poliomyélite', true,'Vaccination obligatoire contre la poliomyélite'),
('4', 'HBV', 'Vaccin Hépatite B','Hépatite B', true,'Vaccination obligatoire contre l’hépatite B'),
('5', 'ROR', 'Vaccin Rougeole-Oreillons-Rubéole','Rougeole, Oreillons, Rubéole', true,'Vaccin combiné obligatoire chez l’enfant'),
('6', 'HIB', 'Vaccin Haemophilus influenzae type b','Infections à Haemophilus influenzae', true,'Prévention des infections bactériennes graves'),
('7', 'PNEUMO', 'Vaccin antipneumococcique','Infections à pneumocoque', true,'Prévention des pneumonies et méningites'),
('8', 'ROTA', 'Vaccin Rotavirus','Gastro-entérite à rotavirus', true,'Vaccination du nourrisson contre les diarrhées sévères'),
-- Vaccinations avec rappels à l’âge adulte
('9', 'DTC-R', 'Rappel Diphtérie-Tétanos-Coqueluche','Diphtérie, Tétanos, Coqueluche', true,'Rappel obligatoire tous les 10 ans'),
-- Vaccinations obligatoires selon contexte
('10', 'VAA', 'Vaccin Fièvre Jaune','Fièvre jaune', true,'Vaccination obligatoire dans les zones endémiques et pour les voyages'),
('11', 'MEN', 'Vaccin Méningocoque','Méningite à méningocoque', true,'Vaccination obligatoire en zone à risque'),
('12', 'COVID19', 'Vaccin COVID-19','COVID-19', false,'Vaccination obligatoire selon contexte sanitaire ou professionnel')

ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
libelle= EXCLUDED.libelle,
maladie_cible=EXCLUDED.maladie_cible,
obligatoire=EXCLUDED.obligatoire,
description= EXCLUDED.description;