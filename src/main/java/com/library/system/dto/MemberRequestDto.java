package com.library.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberRequestDto {

    @NotBlank(message = "İsim boş olamaz!")
    private String firstName;

    @NotBlank(message = "Soyisim boş olamaz!")
    private String lastName;

    @NotBlank(message = "E-posta boş olamaz!")
    @Email(message = "Geçerli bir e-posta adresi giriniz!")
    private String email;

    public MemberRequestDto() {}

    public MemberRequestDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}