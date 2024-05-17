package id.thedev.tumit.sbdemoapi.recaptcha;

public record RecaptchaResponse(Boolean success, String challenge_ts, String hostname, Double score, String action) {}
