package com.MainControl.RestApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/get")
    public Object getAll(){
        //read file into stream, try-with-resources
        Resource resource = new ClassPathResource("JSON/fixtures/products.txt");
        try  {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(),Object.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/search",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getByFilter(@RequestParam String tag1){
        String Result = "{";
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get("JSON/fixtures/products.txt"))) {
            Result += stream
                    .filter(Line -> Line.contains(tag1))
                    .collect(Collectors.joining());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Result+="}";
        return Result;
    }
    @RequestMapping(value = "/all",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String all(){
        return"fixtures/products.txt";
    }


}
