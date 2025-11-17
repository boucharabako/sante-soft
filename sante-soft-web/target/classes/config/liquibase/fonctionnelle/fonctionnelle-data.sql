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
('FC_COMM','Gestion des commandes','/gestion','/gestion/commande', 'FC_HABILIT','FONCTION','PARAM_NVX_SA')
ON CONFLICT (code) DO UPDATE 
SET
libelle= EXCLUDED.libelle,
page_uri_base =EXCLUDED.page_uri_base,
api_uri_base =EXCLUDED.api_uri_base,
parent =EXCLUDED.parent,
type_fonction =EXCLUDED.type_fonction,
niveau_habilitation_max =EXCLUDED.niveau_habilitation_max;