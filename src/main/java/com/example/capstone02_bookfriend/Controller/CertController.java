package com.example.capstone02_bookfriend.Controller;


import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Cert;
import com.example.capstone02_bookfriend.Service.CertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cert")
public class CertController {

    private final CertService certService;

    @GetMapping("/get")
    public ResponseEntity getAllCerts() {
        if (certService.getAllCerts().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no certs"));
        return ResponseEntity.status(200).body(certService.getAllCerts());
    }

    @PostMapping("/add")
    public ResponseEntity addCert(@RequestBody @Valid Cert cert, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = certService.addCert(cert);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new cert is added"));
        return ResponseEntity.status(400).body(new ApiResponse("user or book not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCert(@PathVariable Integer id, @RequestBody @Valid Cert cert, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = certService.updateCert(id, cert);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("not found or already exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCert(@PathVariable Integer id) {
        boolean isDeleted = certService.deleteCert(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }
}
