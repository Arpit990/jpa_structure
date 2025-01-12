package com.example.demo.controller;

import com.example.demo.entity.DesignationMaster;
import com.example.demo.repository.IDesignationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/designation")
public class DesignationController {

    private IDesignationRepository _designationRepository;

    DesignationController(IDesignationRepository designationRepository) {
        _designationRepository = designationRepository;
    }

    @GetMapping("/designationUrl")
    public String Index(Model model) {
        List<DesignationMaster> designationList = _designationRepository.getList(x -> x.getId() == 8);
        System.out.println(designationList);
        model.addAttribute("designationList", designationList);
        return "Designation/designation";
    }

    @PostMapping("/addDesignation")
    public String addDesignation(DesignationMaster designationMaster) {
        _designationRepository.save(designationMaster);
        return "redirect:/designation/designationUrl";
    }

    @GetMapping("/getList")
    public String getList() {
        return "Designation/designation";
    }
}
