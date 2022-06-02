package com.bybiobi.controllers;

import com.bybiobi.dto.ResponseData;
import com.bybiobi.dto.SearchData;
import com.bybiobi.dto.SupplierData;
import com.bybiobi.models.entities.Supplier;
import com.bybiobi.services.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierData supplierData, Errors errors) {

        ResponseData<Supplier> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierData, Supplier.class);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Supplier> findAll() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public Supplier findOne(@PathVariable("id") Long id) {
        return supplierService.findOne(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Supplier>> update(@Valid @RequestBody SupplierData supplierData, Errors errors) {

        ResponseData<Supplier> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierData, Supplier.class);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/byemail")
    public Supplier findByEmail(@RequestBody SearchData searchData) {
        return supplierService.findByEmail(searchData.getSearchKey());
    }

    @PostMapping("/search/byname")
    public List<Supplier> findByName(@RequestBody SearchData searchData) {
        return supplierService.findByName(searchData.getSearchKey());
    }

    @PostMapping("/search/bynamestartwith")
    public List<Supplier> findByNameStartWith(@RequestBody SearchData searchData) {
        return supplierService.findByNameStartWith(searchData.getSearchKey());
    }

    @PostMapping("/search/bynameoremail")
    public List<Supplier> findByNameOrEmail(@RequestBody SearchData searchData) {
        return supplierService.findByNameOrEmail(searchData.getSearchKey(), searchData.getOtherSearchKey());
    }
}
