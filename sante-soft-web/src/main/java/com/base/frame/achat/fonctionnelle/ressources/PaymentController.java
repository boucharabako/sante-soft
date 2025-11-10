/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.ressources;

/**
 *
 * @author NANO TECH
 */


//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

//    @PostMapping("/create-payment-intent")
//    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) throws StripeException {
//        Stripe.apiKey = stripeSecretKey;
//
//        int amount = (int) data.get("amount");
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", amount);
//        params.put("currency", "usd");
//
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("clientSecret", paymentIntent.getClientSecret());
//
//        return response;
//    }
}
