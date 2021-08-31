package com.MainControl.RestApi.ApiController;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class ApiController {

    public String productsPath = "src/main/resources/static/fixtures/products.txt";

    @GetMapping(value = "/getall")
    public String getAll(){
        String Result = "";

        try (Stream<String> stream = Files.lines(Paths.get(productsPath))) {
            Result += stream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result;
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public String getByFilter(@RequestParam String tag){
        String Result = "";
        String tag1 = tag.substring(0, 1).toUpperCase() + tag.substring(1);
        try (Stream<String> stream = Files.lines(Paths.get(productsPath))) {
            Result += stream
                    .filter(Line -> Line.contains(tag1))
                    .collect(Collectors.toList())
                    +"\n";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result;
    }
}
