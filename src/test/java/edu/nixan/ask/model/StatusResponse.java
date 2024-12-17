package edu.qa.ask.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse {

    private String status;

    private String message;
}
