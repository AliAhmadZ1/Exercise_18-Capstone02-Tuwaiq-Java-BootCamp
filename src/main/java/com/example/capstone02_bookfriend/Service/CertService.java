package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.Model.Book;
import com.example.capstone02_bookfriend.Model.Cert;
import com.example.capstone02_bookfriend.Model.User;
import com.example.capstone02_bookfriend.Repository.BookRepository;
import com.example.capstone02_bookfriend.Repository.CertRepository;
import com.example.capstone02_bookfriend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertService {

    private final CertRepository certRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Cert> getAllCerts() {
        return certRepository.findAll();
    }

    public Boolean addCert(Cert cert) {
        Book book = bookRepository.findBooksById(cert.getBook_id());
        User user = userRepository.findUserById(cert.getUser_id());
        if (book==null||user==null)
            return false;
        Cert oldCert = certRepository.findCertByUserId(cert.getUser_id());
        if (oldCert==null) {
            certRepository.save(cert);
            return true;
        }
        if (oldCert.getBook_id() == cert.getBook_id()) {
            oldCert.setAmount(cert.getAmount() + 1);
            certRepository.save(cert);
        }else {
            oldCert.setAmount(1);
            oldCert.setBook_id(cert.getBook_id());
            certRepository.save(cert);
        }
        return true;
    }

    public Boolean updateCert(Integer id, Cert cert) {
        Book book = bookRepository.findBooksById(cert.getBook_id());
        User user = userRepository.findUserById(cert.getUser_id());
        if (book==null||user==null)
            return false;
        Cert oldCert = certRepository.findCertById(id);
        if (oldCert == null)
            return false;

        oldCert.setBook_id(cert.getBook_id());
        oldCert.setUser_id(cert.getUser_id());
        if (oldCert.getUser_id() == cert.getUser_id() || oldCert.getBook_id() == cert.getBook_id())
            oldCert.setAmount(cert.getAmount() + 1);
        else
            oldCert.setAmount(1);
        certRepository.save(oldCert);
        return true;
    }

    public Boolean deleteCert(Integer id){
        Cert cert = certRepository.findCertById(id);
        if (cert==null)
            return false;
        certRepository.delete(cert);
        return true;
    }


}
