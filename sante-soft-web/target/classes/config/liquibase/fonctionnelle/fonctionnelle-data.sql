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
('FC_ETABLISSEMENT','Gestion des établissements','/gestion','/gestion/etablissement', 'FC_HABILIT','FONCTION','PARAM_NVX_SA')

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