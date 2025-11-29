/* 
 * Script d'initialisation pour le profil PATIENT
 * Author:  Bouchara
 * Created: 27 Novembre 2024
 */

-- Insertion du profil PATIENT
INSERT INTO securite.sec_profils(id, code, intitule, description, etat, created_by, last_modified_by) 
VALUES 
('PROFIL_PATIENT','PATIENT', 'Patient','Profil pour les patients du système de santé','PROFIL002','SYSTEM','SYSTEM')

ON CONFLICT (id) DO UPDATE 
SET
code= EXCLUDED.code,
intitule= EXCLUDED.intitule,
description= EXCLUDED.description,
etat= EXCLUDED.etat;

-- Insertion de la fonction de gestion des patients
INSERT INTO  socle.socle_fonction(code,libelle,page_uri_base,api_uri_base,parent,type_fonction,niveau_habilitation_max) 
VALUES 
('FC_PATIENT','Gestion des patients','/gestion','/gestion/patient', 'FC_HABILIT','FONCTION','PARAM_NVX_SA')
ON CONFLICT (code) DO UPDATE 
SET
libelle= EXCLUDED.libelle,
page_uri_base =EXCLUDED.page_uri_base,
api_uri_base =EXCLUDED.api_uri_base,
parent =EXCLUDED.parent,
type_fonction =EXCLUDED.type_fonction,
niveau_habilitation_max =EXCLUDED.niveau_habilitation_max;

