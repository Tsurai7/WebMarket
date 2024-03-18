package org.example.code.utilities;

import lombok.AllArgsConstructor;
import org.example.code.entities.BankCard;
import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.example.code.repositories.BankCardRepository;
import org.example.code.repositories.ProductRepository;
import org.example.code.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final BankCardRepository bankCardRepository;

    @Override
    public void run(String... args) throws Exception {

        Product laptop = new Product("Laptop", "Powerful laptop", "gadgets");
        productRepository.save(laptop);

        Product iphone = new Product("Iphone", "Black color", "gadgets");
        productRepository.save(iphone);

        User john = new User("John", "password");
        john.addProduct(laptop);
        userRepository.save(john);

        BankCard johnsCard = new BankCard("John", "1233131", "08/24", 737);
        johnsCard.setHolder(john);
        bankCardRepository.save(johnsCard);
    }
}