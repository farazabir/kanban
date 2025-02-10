package com.faraz.Kanban.stripe;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/stripe/webhook")
public class WebhookController {
    private static final  String STRIPE_SECRET = "webhook sechret" ;

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, STRIPE_SECRET);
            switch (event.getType()) {
                case "customer.subscription.created":
                    System.out.println("Subscription Created: " + event.getData());
                    break;
                case "invoice.payment_succeeded":
                    System.out.println("Payment Succeeded: " + event.getData());
                    break;
                case "customer.subscription.deleted":
                    System.out.println("Subscription Canceled: " + event.getData());
                    break;
                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }
            return ResponseEntity.ok("Received");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
