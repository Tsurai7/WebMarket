package com.main.webmarket.utilities;

import com.main.webmarket.entities.BankCard;
import com.main.webmarket.entities.Product;
import com.main.webmarket.entities.User;
import com.main.webmarket.repositories.BankCardRepository;
import com.main.webmarket.repositories.ProductRepository;
import com.main.webmarket.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:MissingJavadocMethod"})
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
