package com.rus.nawm.mainservice.controller.rest;

import com.rus.nawm.mainservice.domain.User;
import com.rus.nawm.mainservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        //todo сделать аспектно получение айпи адреса отправителя и сделать метод сохранения в очередь аудита нового события
        /*
        * @Aspect
@Component
public class AuditAspect {

    private final HttpServletRequest request;

    public AuditAspect(HttpServletRequest request) {
        this.request = request;
    }

    // Определение точки среза для всех методов контроллера
    @Pointcut("execution(* com.example.controller.UserController.*(..))")
    public void userControllerMethods() {}

    @AfterReturning(pointcut = "userControllerMethods()", returning = "result")
    public void logAudit(Object result) {
        String clientIp = getClientIp(request);
        // Логика сохранения события в аудите, включая IP-адрес и другие данные
        saveAuditEvent(clientIp, result);
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        return (xForwardedFor != null && !xForwardedFor.isEmpty()) ? xForwardedFor.split(",")[0] : request.getRemoteAddr();
    }

    private void saveAuditEvent(String clientIp, Object result) {
        // Сохранение события в ClickHouse или другой базе данных
    }
}*/
        User createdUser = userService.createUser(user);
        EntityModel<User> entityModel = EntityModel.of(createdUser);
        entityModel.add(linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            EntityModel<User> entityModel = EntityModel.of(user.get());
            entityModel.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
            entityModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
            entityModel.add(linkTo(methodOn(DocumentController.class).getDocumentByUserId(id)).withRel("document"));
            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<User>>> getAllUsers() {
        List<EntityModel<User>> users = userService.getAllUsers()
                .stream()
                .map(user -> {
                    EntityModel<User> entityModel = EntityModel.of(user);
                    entityModel.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
                    return entityModel;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        EntityModel<User> entityModel = EntityModel.of(updatedUser);
        entityModel.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //todo сделать метод для проверки документов который кидает в раббит заявку и затем нотифаер уведомляет, что проверка закончилась
}