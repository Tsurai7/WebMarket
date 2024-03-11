package org.example.code.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String name;

    private String password;

    private Set<ProductDto> productDtos = new HashSet<>();

    private Set<BankCardDto> bankCardDtos= new HashSet<>();

    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
