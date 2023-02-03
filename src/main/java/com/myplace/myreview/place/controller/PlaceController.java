package com.myplace.myreview.place.controller;

import com.myplace.myreview.place.dto.PlaceForm;
import com.myplace.myreview.place.service.PlaceProvider;
import com.myplace.myreview.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/places")
@Controller
public class PlaceController {

    private final PlaceService placeService;
    private final PlaceProvider placeProvider;

    @GetMapping("/add")
    public String addPlace(Model model) {
        model.addAttribute("placeForm", new PlaceForm());
        return "/places/add";
    }

    @PostMapping("/add")
    public String addPlace(PlaceForm placeForm) {
        placeService.save(placeForm);

        return "redirect:/";
    }
}
