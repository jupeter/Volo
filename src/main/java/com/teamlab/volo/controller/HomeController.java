package com.teamlab.volo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jupeter on 07.09.16.
 */

@RequestMapping("/")
@Controller
public class HomeController {

    final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        logger.info("Load main page");

        return "index";
    }
}
