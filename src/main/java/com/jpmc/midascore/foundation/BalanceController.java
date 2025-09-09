package com.jpmc.midascore.foundation;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.web.bind.annotation.*;

@RestController
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam("userId") Long userId) {
        UserRecord user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new Balance(0.0f);  // use float
        }
        return new Balance(user.getBalance());
    }
}

