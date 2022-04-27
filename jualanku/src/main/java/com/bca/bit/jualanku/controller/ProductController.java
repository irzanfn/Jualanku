package com.bca.bit.jualanku.controller;

import com.bca.bit.jualanku.model.*;
import com.bca.bit.jualanku.service.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
public class ProductController {

    private static String UPLOADED_FOLDER = "D://Data//BIT//Peminatan//Project Spring//Project//jualanku//src//main//resources//static//img//upload//";

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/seller/product/new")
    public ModelAndView createProduct() {
        ModelAndView modelAndView = new ModelAndView();
        // create student object to hold student form data
        Product product = new Product();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/seller/product/add");
        return modelAndView;

    }

    @GetMapping("/seller/product/{id}/update")
    public ModelAndView updateProduct(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        Optional<Product> productOptional = productService.findProductById(id);
        if(productOptional.isPresent()) {
            product = productOptional.get();
            modelAndView.addObject("product", product);
            modelAndView.setViewName("/seller/product/edit");
            return modelAndView;
        } else {
            modelAndView.setViewName("/seller/home");
            return modelAndView;
        }

    }

    @PostMapping(value = "/seller/product/{id}/update")
    public String updateProduct(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        if (file.isEmpty()) {
            System.out.println(product.getSeller());
            System.out.println(product.getDateCreated());
            productService.updateProduct(product);
        } else {
            try {
                String origin = UPLOADED_FOLDER + product.getImg();

                String ext = FilenameUtils.getExtension(StringUtils.cleanPath(file.getOriginalFilename()));
                String img = "img-" + UUID.randomUUID().toString() + "." +ext;
                product.setImg(img);

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();

                System.out.println(product.getImg());
                System.out.println(file.getInputStream());
                String uploadDir = UPLOADED_FOLDER + img;
                Files.copy(file.getInputStream(), Path.of(uploadDir));
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User user = userService.findUserByEmail(auth.getName()).get();
                product.setSeller(user);
                productService.saveProduct(product);
                Files.delete(Path.of(origin));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/index";
    }

    @PostMapping(value = "/seller/product/save")
    public String saveProduct(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        if (file.isEmpty()) {
            modelAndView.addObject("error", "Please select a file to upload");
            return "redirect:/seller/product/new";
        }

        try {
            String ext = FilenameUtils.getExtension(StringUtils.cleanPath(file.getOriginalFilename()));
            String img = "img-" + UUID.randomUUID().toString() + "." +ext;
            product.setImg(img);

            // Get the file and save it somewhere
//            System.out.println(product.getImg());
//            System.out.println(file.getInputStream());
            String uploadDir = UPLOADED_FOLDER + img;
            Files.copy(file.getInputStream(), Path.of(uploadDir));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName()).get();
            product.setSeller(user);
            productService.saveProduct(product);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

    @GetMapping("/seller/product/{id}/detail")
    public ModelAndView detailProdouct(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        Optional<Product> productOptional = productService.findProductById(id);
        if(productOptional.isPresent()){
            product = productOptional.get();
            modelAndView.addObject("product", product);
            modelAndView.setViewName("/seller/product/detail");
            return modelAndView;
        } else {
            modelAndView.setViewName("/seller/home");
            return modelAndView;
        }

    }

    @PostMapping("/seller/product/{id}/delete")
    public String deleteProduct(@PathVariable Long id) throws IOException {
        Product product = new Product();
        Optional<Product> productOptional = productService.findProductById(id);
        if(productOptional.isPresent()) {
            product = productOptional.get();
            String origin = UPLOADED_FOLDER + product.getImg();
            productService.deleteProduct(id);
            Files.delete(Path.of(origin));
        }
        return "redirect:/index";

    }

    @GetMapping("/buyer/product")
    public ModelAndView productAll(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();
        List<Product> products = productService.findAllProducts();
        Cart cart = new Cart();
        Optional<Cart> optionalCart = cartService.findCartByBuyerId(user);
        if(optionalCart.isPresent()){
            cart = optionalCart.get();
            Integer cartItem = cartItemService.countCartItemByCartId(cart);
            modelAndView.addObject("cart", cart);
            modelAndView.addObject("countCartItem", cartItem);
        }
        CartItem cartItem = new CartItem();
        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = walletService.findWalletByBuyer(user);
        if (optionalWallet.isPresent()){
            wallet = optionalWallet.get();
        }
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("cartItem", cartItem);
        modelAndView.addObject("productList", products);
        modelAndView.addObject("userName", "Welcome " + user.getId() + "/" + user.getName()  + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Buyer Role");
        modelAndView.setViewName("buyer/product");
        return modelAndView;
    }
}
