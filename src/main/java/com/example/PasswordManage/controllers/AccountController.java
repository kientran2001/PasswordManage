package com.example.PasswordManage.controllers;

import com.example.PasswordManage.models.Account;
import com.example.PasswordManage.models.User;
import com.example.PasswordManage.repositories.AccountRepository;
import com.example.PasswordManage.repositories.UserRepository;
import com.example.PasswordManage.services.AccountService;
import com.example.PasswordManage.services.EncodeService;
import com.example.PasswordManage.services.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.OptionalInt;

@Controller
@RequestMapping(path = "account")
public class AccountController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    private static String masterPassword;
    private SecretKey keyLoad;
    private SecretKey key;
    private SecretKeySpec Ks;
    private String str, seed;

    @RequestMapping(value = "/allAccounts/{userId}.{masterPass}.{hmac}", method = RequestMethod.GET)
    public String getAccountsByUserId(ModelMap modelMap,
                                      @PathVariable String masterPass,
                                      @PathVariable int userId) {
        masterPassword = masterPass;
        Iterable<Account> accounts = accountRepository.findByUserId(userId);
        modelMap.addAttribute("accounts", accounts);
        modelMap.addAttribute("userId", userId);
        return "accountList";
    }

    @RequestMapping(value = "/insertAccount/{userId}", method = RequestMethod.GET)
    public String insertAccount(ModelMap modelMap, @PathVariable int userId) {
        Account account = new Account();
        account.setUserId(userId);
        modelMap.addAttribute("account", account);
        return "insertAccount";
    }

    @RequestMapping(value = "/insertAccount/{userId}", method = RequestMethod.POST)
    public String insertAccount(ModelMap modelMap,
                                @PathVariable int userId,
                                @Valid @ModelAttribute("account") Account account,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            modelMap.addAttribute("account", account);
            return "insertAccount";
        }

        try {
            Optional<User> user = userRepository.findById(userId);
            String username = user.get().getUsername();

            keyLoad = KeyService.loadSeedFromKeyStore("ks" + username + ".keystore", masterPassword);
            str = keyLoad.toString();
            seed = str.substring(str.length() - 16);
            KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), seed.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = factory.generateSecret(spec);
            Ks = new SecretKeySpec(key.getEncoded(), "AES");

            byte[] encryptedPass = EncodeService.encrypt(account.getPass().trim().getBytes(), key, seed.getBytes());
            String acc = account.getAcc().trim();
            String pass = Base64.getEncoder().encodeToString(encryptedPass);
            String web = account.getWeb().trim();

            Account savedAccount = accountService.createAccount(userId, acc, pass, web);
            if(savedAccount.getId() > 0) {
                System.out.println("Insert account successfully");
                return "redirect:../allAccounts/" + userId + "." + masterPassword + "." + user.get().getHmac();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return "redirect:/account/insertAccount";
        }
        return "insertAccount";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String detailAccount(ModelMap modelMap, @PathVariable int id) {
        Optional<Account> account = accountRepository.findById(id);
        int userId = account.get().getUserId();
        Optional<User> user = userRepository.findById(userId);
        String username = user.get().getUsername();
        String ksFile = "ks" + username + ".keystore";
        keyLoad = KeyService.loadSeedFromKeyStore(ksFile, masterPassword);
        str = keyLoad.toString();
        seed = str.substring(str.length() - 16);


        String pass = account.get().getPass();
        byte[] encryptPass = Base64.getDecoder().decode(pass);
        try {
            KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), seed.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = factory.generateSecret(spec);
            String decryptPass = EncodeService.decrypt(encryptPass, key, seed.getBytes());
            account.get().setPass(decryptPass);
            modelMap.addAttribute("account", account.get());
            return "detailAccount";
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return "redirect";
        }
    }

    @RequestMapping(value = "/deleteAccount/{id}", method = RequestMethod.POST)
    public String deleteAccount(ModelMap modelMap, @PathVariable int id) {
        Optional<Account> account = accountRepository.findById(id);
        int userId = account.get().getUserId();
        Optional<User> user = userRepository.findById(userId);
        System.out.println("Delete!!!");
        accountRepository.deleteById(id);
        return "redirect:../allAccounts/" + userId + "." + masterPassword + "." + user.get().getHmac();
    }
}
