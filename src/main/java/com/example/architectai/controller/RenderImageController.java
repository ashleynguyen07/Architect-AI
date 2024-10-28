package com.example.architectai.controller;

import com.example.architectai.dto.RenderImageRequestDto;
import com.example.architectai.entity.RenderImageInfo;
import com.example.architectai.service.PaypalServiceImpl;
import com.example.architectai.service.RenderImageService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RenderImageController {

    private final RenderImageService renderImageService;
    private final PaypalServiceImpl paypalService;

    @Value("${app.domain}")
    private String domain;

    @PostMapping("/render")
    public ResponseEntity<RenderImageInfo> renderImage(
            @RequestParam(value = "image") MultipartFile image,
            @RequestParam(value = "style") String style,
            @RequestParam(value = "material") String material,
            @RequestParam(value = "width") Integer width,
            @RequestParam(value = "height") Integer height,
            @RequestParam(value = "numInferenceSteps", required = false, defaultValue = "") Integer numInferenceSteps,
            @RequestParam(value = "guidanceScale", required = false, defaultValue = "") Number guidanceScale) throws IOException {

        RenderImageRequestDto requestDto = RenderImageRequestDto.builder()
                .style(style)
                .material(material)
                .width(width)
                .height(height)
                .numInferenceSteps(numInferenceSteps)
                .guidanceScale(guidanceScale)
                .build();
        return ResponseEntity.ok(renderImageService.renderImage(requestDto, image));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportImage(@RequestParam(value = "file-name") String name) throws IOException {
        byte[] imageData = renderImageService.exportImage(name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // Set the appropriate media type
        headers.setContentDispositionFormData("attachment", name + ".png"); // Set the file name and extension

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageData);
    }

    @GetMapping("/paypal")
    public String home() {
        return "paypal";
    }

    @PostMapping("/paypal")
    public RedirectView createPayment() {
        try {
            String cancleUrl = domain + "/paypal/cancel";
            String successUrl = domain + "/paypal/success";
            Payment payment = paypalService.createPayment(
                    0.01,
                    "USD",
                    "paypal",
                    "sale",
                    "This is a test",
                    cancleUrl,
                    successUrl
            );
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new RedirectView(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/paypal/success")
    public String success(@RequestParam("paymentId") String paymentId,
                          @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("/paypal/cancel")
    public String cancel() {
        return "cancel";
    }

    @GetMapping("/paypal/error")
    public String error() {
        return "error";
    }
}
