package com.base.frame.conf;
import com.base.frame.account.core.repository.UtilisateurPasswordRepository;
import com.base.frame.account.core.repository.HabilitationRepository;
import com.base.frame.account.entity.*;
import com.base.frame.account.entity.Habilitation;
import com.base.frame.account.core.repository.UtilisateurRepository;
import com.base.frame.account.core.repository.UtilisateurProfilRepository;
import com.base.frame.account.dto.UtilisateurDTO;

import com.base.frame.socle.core.entity.Fonction;
import com.base.frame.socle.core.repository.FonctionRepository;
import com.base.frame.socle.core.utils.SocleConstant;
import liquibase.pro.packaged.ha;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import  com.base.frame.account.exceptions.*;
//import com.base.frame.account.service.UtilisateurService;
import  com.base.frame.socle.core.utils.Util;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    public static final Integer MAX_AUTHORITY = 3;


    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private UtilisateurProfilRepository utilisateurProfilRepository;

    @Autowired
    private HabilitationRepository habilitationRepository;
    @Autowired
    private FonctionRepository fonctionRepository;
    @Autowired 
    private UtilisateurPasswordRepository utilisateurPasswordRepository;

    private static final String USER = "User ";
    private static final String WAS_NOT_FOUND = " was not found";
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DomainUserDetailsService.class);
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        LOG.info("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        System.out.println("aaaaaaaaaaaaa:"+login);
        Optional<Utilisateur> userFromDatabase = utilisateurRepository.findByUsername(lowercaseLogin);
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setTel("93222506");
        
        return userFromDatabase.map(user -> {
            Set<String> authorities = new HashSet<>();

            if (!user.getUsername().equals(login) && !user.getEmail().equals(login)) {
                throw new UserNotActivatedException(USER + lowercaseLogin + " WAS_NOT_FOUND");
            }
//            if (!user.getEtat().getCodeEtat().equals(SocleConstant.CODIFICATION_ETAT_ACTIF)) {
//                throw new UserNotActivatedException(USER + lowercaseLogin + " WAS_NOT_ACTIVATED");
//            }
           Optional<UtilisateurPassword> up=utilisateurPasswordRepository.findByUserId(user.getId());
            if (!up.isPresent()) {
                throw new UserNotActivatedException(USER + lowercaseLogin + " PWD_WAS_NOT_FOUND");
            }
            List<Profil> lps = utilisateurProfilRepository.findProfilByUserId(user.getId());

            lps.forEach(p -> {
                System.out.println("======== p" + p.toString());
                List<Habilitation> hb = habilitationRepository.findByProfil(p.getId());
                System.out.println("La taille:"+hb.size());

                hb.stream().filter(x->x.getFonction()!=null &&(x.getFonction().getCode()!=null
                        ))

                        . forEach((a) -> {
                             System.out.println("======== a:" + a.getFonction().getCode());
                            authorities.add(a.getFonction().getCode());
                            Integer i = Integer.valueOf(a.getNiveauHabilitation().getCode());
                            Util.findChildren(i).stream().forEach(j -> {
                                authorities.add(a.getFonction().getCode()+ "." + j);
                            });

                            String parent =a.getFonction().getParent();


                            while (!(parent==null || parent.isEmpty())){
                               Optional<Fonction> pa= fonctionRepository.findById(parent);
                                 if(pa.isPresent()){
                                     authorities.add(pa.get().getCode());
                                     parent=pa.get().getParent();
                                 }else{
                                     parent=null;
                                 }
                            }


                        });

            });
            authorities.add("CONNECTED");

            LOG.info("Les fonctions:"+authorities);
            user.setAuthorities(authorities);

            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    up.get().getPassword(),
                    grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException(USER + lowercaseLogin + " was not found in the "
                + "database"));

    }


}