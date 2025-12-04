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