package ru.urabov.lab8.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.urabov.lab8.entity.Student;
import ru.urabov.lab8.repository.StudentRepository;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("/list")
    public ModelAndView getAllStudents(Principal principal) {
        log.info("/list -> {}", principal.getName());
        ModelAndView mav = new ModelAndView("list-students");
        mav.addObject("students", studentRepository.findAll(Sort.by("surname", "name")));
        return mav;
    }

    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm() {
        return new ModelAndView("add-student-form").addObject("student", new Student());
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Студент с id = " + studentId + " не найден"));
        return new ModelAndView("add-student-form").addObject("student", student);
    }

    @PostMapping("/saveStudent")
    public ModelAndView saveStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult,
                                    Principal principal) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-student-form").addObject("student", student);
        }
        studentRepository.save(student);
        log.info("{} сохранил студента {}", principal.getName(), student);
        return new ModelAndView("redirect:/list");
    }

    // Удаление изменяет данные, поэтому выполняется POST-запросом, а не ссылкой GET
    @PostMapping("/deleteStudent")
    public String deleteStudent(@RequestParam Long studentId, Principal principal) {
        studentRepository.deleteById(studentId);
        log.info("{} удалил студента id = {}", principal.getName(), studentId);
        return "redirect:/list";
    }
}
