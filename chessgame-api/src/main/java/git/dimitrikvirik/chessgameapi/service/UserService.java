//package git.dimitrikvirik.chessgameapi.service;
//
//import git.dimitrikvirik.chessgameapi.model.domain.UserAccount;
//import git.dimitrikvirik.chessgameapi.repository.UserAccountRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserAccountRepository userAccountRepository;
//
//
//    @Transactional
//    public UserAccount save(UserAccount userAccount) {
//        return userAccountRepository.save(userAccount);
//    }
//
//
//    @Transactional
//    public Optional<UserAccount> get(long id) {
//        return userAccountRepository.findById(id);
//    }
//
//    @Transactional
//    public Optional<UserAccount> getByEmail(String email) {
//        return userAccountRepository.findByEmail(email);
//    }
//
//    @Transactional
//    public Optional<UserAccount> getByUsername(String username) {
//        return userAccountRepository.findByUsername(username);
//    }
//
//
//}
