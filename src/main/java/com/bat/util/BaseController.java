package com.bat.util;

import com.bat.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseController {
    @Autowired
    protected UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        // Spring class for string trim
        // "true" in the constructor means it will trim the spaces down to null
        // if there's only white spaces in the data filed
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);

        // registering it as a custom editor that will work on every String class as specified
        webDataBinder.registerCustomEditor(String.class, stringTrimmer);
    }
}
