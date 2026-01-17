package com.intellipay.dto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class ApiResponse<T> {
    private String message;
    private T data;
}
