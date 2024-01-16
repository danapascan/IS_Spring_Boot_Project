package com.example.demo.controller;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Book;
import com.example.demo.model.BookDetails;
import com.example.demo.model.RegistrationRequest;
import com.example.demo.model.User;
import com.example.demo.service.BookService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookService bookService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public String getUsers(Model model){
        List<UserDto> userDtos = userService.getAllUsers();
        model.addAttribute("title", "Users");
        model.addAttribute("users", userDtos);
        return "users";
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping("/user/all")
    public String displayAllBooks(@RequestParam(required = false) Integer bookId, Model model) {
        if (bookId == null) {
            model.addAttribute("books", bookService.findAll());
        }
        return "user/all";
    }
    @GetMapping("/user/create")
    public String displayCreateBookForm(Model model) {
        model.addAttribute(new Book());
        return "user/create";
    }
    @PostMapping("/user/create")
    public String createBook(@ModelAttribute Book newBook, Errors errors) {
        if (errors.hasErrors()) {
            return "user/create";
        }
        bookService.save(newBook);
        return "redirect:/user/all";
    }

    @GetMapping("/user/detail")
    public String displayBookDetails(@RequestParam Integer bookId, Model model) {
        Optional<Book> result = bookService.findById(bookId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + bookId);
        } else {
            Book book = result.get();
            model.addAttribute("title", book.getTitle() + " Details ");
            model.addAttribute("book", book);
        }
        return "user/detail";
    }

    @GetMapping("/user/edit")
    public String displayEditForm(@RequestParam Integer bookId, Model model) {
        Optional<Book> result = bookService.findById(bookId);

        if (result.isPresent()) {
            Book book = result.get();
            model.addAttribute("book", book);
            model.addAttribute("bookId", bookId);
            return "user/edit";
        } else {
            model.addAttribute("error", "Book not found");
            return "error-page";
        }
    }

    @PostMapping("/user/edit")
    public String processEditForm(@RequestParam Integer bookId,@ModelAttribute @Valid Book editedBook, Model model, Errors errors) {
        if (!errors.hasErrors()) {
            Optional<Book> originalBookOptional = bookService.findById(bookId);

            if (originalBookOptional.isPresent()) {
                Book originalBook = originalBookOptional.get();
                originalBook.setTitle(editedBook.getTitle());
                originalBook.getBookDetails().setAuthor(editedBook.getBookDetails().getAuthor());
                originalBook.getBookDetails().setPublishedDate(editedBook.getBookDetails().getPublishedDate());
                originalBook.getBookDetails().setPrice(editedBook.getBookDetails().getPrice());
                originalBook.getBookDetails().setStock(editedBook.getBookDetails().getStock());
                bookService.save(originalBook);

                return "redirect:/user/all";
            }
        }
        return "redirect:/user/edit";
    }

    @GetMapping("/user/delete")
    public String displayDeleteBookForm(Model model) {
        model.addAttribute("title", "Delete Books");
        model.addAttribute("books", bookService.findAll());
        return "user/delete";
    }

    @PostMapping("/user/delete")
    public String processDeleteBookForm(@RequestParam(required = false) int[] bookIds) {
        if (bookIds != null) {
            for (int id : bookIds) {
                bookService.deleteById(id);
            }
        }
        return "redirect:/user/all";
    }
    @GetMapping("admin/create")
    public String displayCreateUserForm(Model model) {
        model.addAttribute(new User());
        model.addAttribute("roles",roleService.findAll());
        return "admin/create";
    }

    @PostMapping("admin/create")
    public String createUser(@ModelAttribute("user") RegistrationRequest registrationRequest, Errors errors) {
        if (errors.hasErrors()) {
            return "admin/create";
        }
        UserDto userDto = userService.registerUser(registrationRequest);
        return "redirect:/users";
    }

    @GetMapping("admin/detail")
    public String displayUserDetails(@RequestParam String userName, Model model) {
        UserDto result = userService. getUserByUsername(userName);
        model.addAttribute("title", " Details ");
        model.addAttribute("user", result);
        return "admin/detail";
    }

    @GetMapping("admin/edit")
    public String displayEditFormUser(@RequestParam String userName, Model model) {
        UserDto result = userService. getUserByUsername(userName);
        model.addAttribute("user", result);
            model.addAttribute("roles",roleService.findAll());
            model.addAttribute("userName", userName);
            return "admin/edit";
    }

    @PostMapping("admin/edit")
    public String processEditFormUser(@RequestParam String userName,@ModelAttribute @Valid User editedUser, Model model, Errors errors) {
        if (!errors.hasErrors()) {
            User user= userService.getByUsername(userName);
            System.out.println(user.getUsername());
            user.setUsername(editedUser.getUsername());
            user.setFirstName(editedUser.getFirstName());
            user.setLastName(editedUser.getLastName());
            user.setEmailAddress(editedUser.getEmailAddress());
            user.setRoles(editedUser.getRoles());
            userService.updateUser(user);
                return "redirect:/users";
            }
        return "redirect:/users";
    }

    @GetMapping("admin/delete")
    public String displayDeleteUserForm(Model model) {
        model.addAttribute("title", "Delete Users");
        model.addAttribute("users", userService.getAllUsers());
        return "admin/delete";
    }

    @PostMapping("admin/delete")
    public String processDeleteUserForm(@RequestParam(required = false) String[] userNames) {
        if (userNames != null) {
            for (String name : userNames) {
                User user= userService.getByUsername(name);
                userService.deleteUser(user);
            }
        }
        return "redirect:/users";
    }
}
