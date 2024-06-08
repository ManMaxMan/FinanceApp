package com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.http;


import com.github.ManMaxMan.FinanceApp.serviceClassifier.controller.converter.api.IConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.dto.*;
import com.github.ManMaxMan.FinanceApp.serviceClassifier.service.api.interfaces.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classifier/operation/category")
public class CategoryController {

    private final ICategoryService categoryService;
    private final IConverterToDTO converterToDTO;

    public CategoryController(ICategoryService categoryService, IConverterToDTO converterToDTO) {
        this.categoryService = categoryService;
        this.converterToDTO = converterToDTO;
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<PageOfCategoryDTO> getPageCategory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "20") Integer size) {
        PageOfCategoryEntityDTO pageOfCategoryEntityDTO = new PageOfCategoryEntityDTO();
        pageOfCategoryEntityDTO.setNumber(page);
        pageOfCategoryEntityDTO.setSize(size);

        PageOfCategoryDTO pageOfCategoryDTO= converterToDTO.convert(categoryService.getPage(pageOfCategoryEntityDTO));

        return ResponseEntity.status(HttpStatus.OK).body(pageOfCategoryDTO);
    }
}
