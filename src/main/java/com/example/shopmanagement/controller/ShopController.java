package com.example.shopmanagement.controller;
import Excel.ExcelHelper;
import com.example.shopmanagement.ExcelExport.ExportHelper;
import com.example.shopmanagement.model.Shop;
import com.example.shopmanagement.repository.ShopRepo;
import com.example.shopmanagement.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    ShopService service;


    @GetMapping("/")
    public String viewRegistraionPage(Model model) {
        Shop shop = new Shop();
        model.addAttribute("shop", shop);
        return "add-shop";
    }

    @Autowired
    ShopRepo repo;

/*    @GetMapping("/index/page/{page}")
    public String showShopList(@PathVariable("page") Integer page, Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Shop> shopPage = repo.findAll(pageable);
        model.addAttribute("shop", shopPage);
        return "index";
    }*/

    @PostMapping("/addshop")
    public String addShop(@Valid Shop shop, BindingResult result) {
        service.adduser(shop, result);
        return "add-shop";

    }

    @GetMapping("/edit/{id}")
    public String findShopById(@PathVariable("id") long id, Model model) {
        Shop shop = service.findShopById(id);
        model.addAttribute("shop", shop);
        return "update-shop";

    }

    @PostMapping("update/{id}")
    public String updateShop(@PathVariable("id") long id, @Valid Shop shop, BindingResult result) {
        if (result.hasErrors()) {
            return "update-shop";
        }
        service.updateShop(id, shop);
        return "redirect:/index";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        Shop shop = service.findShopById(id);
        service.deleteUser(shop);
        return "redirect:/index";


    }

    @GetMapping("/index/search")
    public String findUserBySearchId(@RequestParam("searchid") long id, Model model) {
        Shop shop = service.findShopById(id);
        model.addAttribute("shop", shop);
        return "searchdisplay";
    }


    @GetMapping("/index/page/{page}")
    public String showShopList(
            @PathVariable Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy).descending());

        Page<Shop> shopPage = repo.findAll(pageable);

        model.addAttribute("shop", shopPage);
        model.addAttribute("sortBy", sortBy);

        return "index";
    }


    @PostMapping("/shops/import")
    public String importShops(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        try {
            // Step 1: Validate the uploaded Excel file
            if (file.isEmpty() || !ExcelHelper.CheckExcelFormat(file)) {
                redirectAttributes.addFlashAttribute(
                        "message", "Please upload a valid .xlsx file");
                return "redirect:/index/page/0";
            }

            // Step 2: Convert Excel data into a List of Shop objects
            List<Shop> shops = ExcelHelper.ConvertExcelToListOfShop(
                    file.getInputStream());

            // Step 3: Save all Shop objects into H2
            if (shops.isEmpty()) {
                redirectAttributes.addFlashAttribute(
                        "message", "No shop records found in Excel");
            } else {
                repo.saveAll(shops);

                redirectAttributes.addFlashAttribute(
                        "message", shops.size() + " shops imported successfully");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message", "Excel import failed: " + e.getMessage());
        }

        // Step 4: Return to your existing paginated table
        return "redirect:/index/page/0";
    }


    @GetMapping("/shops/export")
    public ResponseEntity<InputStreamResource> exportShops()
            throws IOException {

        List<Shop> shops = repo.findAll();

        ByteArrayInputStream excelFile =
                ExportHelper.DataToExcel(shops);

        InputStreamResource resource =
                new InputStreamResource(excelFile);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Shops-Export.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }




}
