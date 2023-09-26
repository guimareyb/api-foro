package com.foro.apiforo.Controller;

import com.foro.apiforo.domain.user.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
//@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ResponseDataUser> insertUser (@Valid @RequestBody InsertDataUser insertDataUser, UriComponentsBuilder uriComponentsBuilder){
        User user = userRepository.save(new User(insertDataUser));
        ResponseDataUser responseDataUser = new ResponseDataUser(user);
        URI url = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(url).body(responseDataUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataUser> responseDataUser(@PathVariable Long id){
        User user = userRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseDataUser(user));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseDataUser>> listUsers(@PageableDefault(size = 10, sort = "email") Pageable pageable){
        return ResponseEntity.ok(userRepository.findByFlagTrue(pageable).map(ResponseDataUser::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ResponseDataUser> updateUser(@RequestBody @Valid UpdateDataUser updateDataUser){
        User user = userRepository.getReferenceById(updateDataUser.id()); // Traer usuario de la Base de Datos
        user.updateData(updateDataUser); // Actualizar usuario
        ResponseDataUser responseDataUser = new ResponseDataUser(user);
        return ResponseEntity.ok(responseDataUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        User user = userRepository.getReferenceById(id);
        user.disableUser();
        return ResponseEntity.noContent().build();
    }

}
