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
        return ResponseEntity.status(200).body(certService.getAllCerts());
    }

    @PostMapping("/add")
    public ResponseEntity addCert(@RequestBody @Valid Cert cert, Errors errors) {
        certService.addCert(cert);
        return ResponseEntity.status(200).body(new ApiResponse("new cert is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCert(@PathVariable Integer id, @RequestBody @Valid Cert cert, Errors errors) {
        certService.updateCert(id, cert);
        return ResponseEntity.status(200).body(new ApiResponse("is updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCert(@PathVariable Integer id) {
        certService.deleteCert(id);
        return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
    }
}
