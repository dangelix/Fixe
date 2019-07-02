package com.tikal.mensajeria.prueba;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(value="/prueba")
public class prueba {

	 @RequestMapping(value={"/prueba"},method = RequestMethod.GET)

	   public void prueba(HttpServletResponse response, HttpServletRequest request) throws IOException {
		   response.getWriter().println("Prueba del mètodo PROBAR");

	    }


    @RequestMapping("/index")
    public String index(){
        System.out.println("Looking in the index controller.........");
        return "login.html";
    }

}