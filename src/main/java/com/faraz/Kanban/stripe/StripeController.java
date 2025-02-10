package com.faraz.Kanban.stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {
    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-customer")
    public ResponseEntity<String> createCustomer(@RequestParam String email) {
        try {
            String customerId = stripeService.createCustomer(email);
            return ResponseEntity.ok(customerId);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestParam String customerId, @RequestParam String priceId) {
        try {
            Session session = stripeService.createCheckoutSession(customerId, priceId);
            return ResponseEntity.ok(session.getUrl());
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
