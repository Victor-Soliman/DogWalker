package main.exeptions;

import lombok.Value;


import java.util.List;

@Value
public class ErrorResponse {
    String validation_failed;
    List<String> details;
}
