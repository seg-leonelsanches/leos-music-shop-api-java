package br.com.segment.leosmusicstoreapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
