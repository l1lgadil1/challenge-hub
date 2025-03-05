package com.challengehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/challenge-progress")
    public ModelAndView showChallengeProgress(@RequestParam(required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("challenge-progress");
        return modelAndView;
    }
} 