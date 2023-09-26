package com.foro.apiforo.domain.user;

public record ResponseDataUser(
        Long id,
        String email,
        UserType userType,
        String name,
        String lastname
) {

    public ResponseDataUser (User user){
        this(
                user.getId(),
                user.getEmail(),
                user.getUserType(),
                user.getName(),
                user.getLastname()
        );
    }
}
