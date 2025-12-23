/*
 * Script d'initialisation pour le profil PROFESSIONNEL_SANTE
 * Author:  Bouchara
 * Created: 30 Novembre 2025
 */

-- Insertion du profil PROFESSIONNEL_SANTE
INSERT INTO securite.sec_profils(id, code, intitule, description, etat, created_by, last_modified_by)
VALUES
('PROFIL_PROFESSIONNEL_SANTE','PROFESSIONNEL_SANTE', 'Professionnel de Santé','Profil pour les professionnels de santé du système','PROFIL002','SYSTEM','SYSTEM')

ON CONFLICT (id) DO UPDATE
SET
code= EXCLUDED.code,
intitule= EXCLUDED.intitule,
description= EXCLUDED.description,
etat= EXCLUDED.etat;

