package com.dwibagus.postcollab.response;

import org.springframework.stereotype.Component;

@Component
public class CommonResponseGenerator<T> {

    public <T> CommonResponse<T> successResponse(T datas, String message){
        CommonResponse commonResponse = new CommonResponse<>();
        commonResponse.setStatus("200");
        commonResponse.setMessage(message);
        commonResponse.setDatass(datas);

        return commonResponse;
    }
}
