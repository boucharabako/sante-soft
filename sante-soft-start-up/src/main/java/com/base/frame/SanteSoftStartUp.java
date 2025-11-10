/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame;

//import com.base.frame.account.conf.UserConfig;
//import com.base.frame.account.core.repository.HabilitationRepository;
//import com.base.frame.account.core.repository.UtilisateurPasswordRepository;
//import com.base.frame.account.core.repository.UtilisateurProfilRepository;
//import com.base.frame.account.iservice.IProfilService;
//import com.base.frame.account.iservice.IUtilisateurService;
//import com.base.frame.socle.core.iservice.ISocleGenericService;
//import com.base.frame.achat.fonctionnelle.services.sms.SendSMSService;
import java.util.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author Bouchara
 */
@SpringBootApplication
public class SanteSoftStartUp extends SpringBootServletInitializer {

   
//    @Autowired(required = false)
//    private UserConfig userConfig;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SanteSoftStartUp.class, args);
    }

    @Bean
    public TemplateEngine templateEngine(ITemplateResolver templateResolver) {
//    public TemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(templateResolver);
//        engine.addDialect(sec);
        return engine;
        //

    }

   

    
}
