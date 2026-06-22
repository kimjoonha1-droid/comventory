package com.oracle.comventory.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.comventory.domain.product.Product;
import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.service.statusType.StatusTypeService;

import jakarta.servlet.http.HttpSession;

import com.oracle.comventory.service.product.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final StatusTypeService statusTypeService;

    // 메인
    @GetMapping("/productMain")
    public String main() {
        return "product/productMain";
    }

 // 제품 목록 + 검색 + 페이징
    @GetMapping("/list")
    public String list(ProductDto productDto,
                       Model model,
                       HttpSession session) {

        log.info("ProductController list start...");

        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        boolean canManageProduct =
                loginUser != null
             && (loginUser.getUser_access() == 1
                 || loginUser.getDept_code() == 5500);

        String currentPage = productDto.getCurrentPage();

        if(currentPage == null) {
            currentPage = "1";
        }

        int currentPageInt = Integer.parseInt(currentPage);

        int rowPage = 10;

        int start = (currentPageInt - 1) * rowPage + 1;
        int end   = start + rowPage - 1;

        productDto.setStart(start);
        productDto.setEnd(end);

        Long total = productService.productTotalCount(productDto);

        List<ProductDto> productList =
                productService.findAllProduct(productDto);

        int pageCount =
                (int)Math.ceil((double)total / rowPage);

        int pageBlock = 10;

        int startPage =
                ((currentPageInt - 1) / pageBlock)
                * pageBlock + 1;

        int endPage = startPage + pageBlock - 1;

        if(endPage > pageCount) {
            endPage = pageCount;
        }

        model.addAttribute("productList", productList);

        model.addAttribute("currentPage", currentPageInt);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);

        model.addAttribute("productDto", productDto);
        model.addAttribute("productStatus", productDto.getProductStatus());

        // JSP 버튼 제어용
        model.addAttribute("canManageProduct", canManageProduct);

        return "product/productList";
    }

 // 제품 등록 화면
    @GetMapping("/saveForm")
    public String saveForm(Model model,
                           HttpSession session,
                           RedirectAttributes rttr) {

        if (!canManageProduct(session)) {
            rttr.addFlashAttribute("msg", "제품 관리 권한이 없습니다.");
            return "redirect:/product/list";
        }

        log.info("ProductController saveForm start...");

        Long nextProductCode = productService.findNextProductCode();
        model.addAttribute("nextProductCode", nextProductCode);

        model.addAttribute("productCategoryList",
                statusTypeService.findByBcode(230));

        model.addAttribute("productStatusList",
                statusTypeService.findByBcode(240));

        return "product/productSaveForm";
    }

    @PostMapping("/save")
    public String save(ProductDto productDto,
                       HttpSession session,
                       RedirectAttributes rttr) {

        log.info("ProductController save start productDto -> {}", productDto);

        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        if (loginUser == null) {

            rttr.addFlashAttribute("msg", "로그인 후 이용해주세요.");

            return "redirect:/login";
        }

        if (!canManageProduct(session)) {
            rttr.addFlashAttribute("msg", "제품 등록 권한이 없습니다.");
            return "redirect:/product/list";
        }

        Product product = new Product(
                productDto.getProductCode(),
                productDto.getProductName(),
                productDto.getProductCategory(),
                productDto.getProductStatus(),
                productDto.getProductPrice(),
                productDto.getProductProperQty(),
                1,
                Long.valueOf(loginUser.getEmp_no())
        );

        productService.productSave(product);

        rttr.addFlashAttribute("msg", "제품이 등록되었습니다.");

        return "redirect:/product/list";
    }

    // 제품 수정 화면
    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("product_code") Long product_code,
                             Model model,
                             HttpSession session,
                             RedirectAttributes rttr) {

        if (!canManageProduct(session)) {
            rttr.addFlashAttribute("msg", "제품 수정 권한이 없습니다.");
            return "redirect:/product/list";
        }

        log.info("ProductController updateForm start product_code -> {}", product_code);

        model.addAttribute("product",
                productService.findById(product_code));

        model.addAttribute("productCategoryList",
                statusTypeService.findByBcode(230));

        model.addAttribute("productStatusList",
                statusTypeService.findByBcode(240));

        return "product/productUpdateForm";
    }

 // 제품 수정
    @PostMapping("/update")
    public String update(ProductDto productDto,
                         HttpSession session,
                         RedirectAttributes rttr) {

        if (!canManageProduct(session)) {
            rttr.addFlashAttribute("msg", "제품 수정 권한이 없습니다.");
            return "redirect:/product/list";
        }

        log.info("ProductController update start productDto -> {}", productDto);

        Product originProduct =
                productService.findById(productDto.getProductCode());

        Product product = new Product(
                productDto.getProductCode(),
                productDto.getProductName(),
                productDto.getProductCategory(),
                productDto.getProductStatus(),
                productDto.getProductPrice(),
                productDto.getProductProperQty(),
                productDto.getProductDelStatus(),
                originProduct.getReg_emp_no()
        );

        productService.update(product);
        rttr.addFlashAttribute("msg", "제품 정보가 수정되었습니다.");

        return "redirect:/product/list";
    }

    // 제품 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam("product_code") Long product_code,
                         HttpSession session,
                         RedirectAttributes rttr) {

        if (!canManageProduct(session)) {
            rttr.addFlashAttribute("msg", "제품 상태변경 권한이 없습니다.");
            return "redirect:/product/list";
        }

        log.info("ProductController delete start product_code -> {}", product_code);

        productService.deleteById(product_code);

        rttr.addFlashAttribute("msg", "제품 상태가 변경되었습니다.");

        return "redirect:/product/list";
    }
    
    @ResponseBody
    @GetMapping("/bomDetail")
    public List<ProductDto> bomDetail(
            @RequestParam("productCode") Long productCode) {

        return productService.findBomDetail(productCode);
    }
    
    private boolean canManageProduct(HttpSession session) {

        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        return loginUser != null
            && (loginUser.getUser_access() == 1
                || loginUser.getDept_code() == 5500);
    }
}