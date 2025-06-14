package br.com.lucas.santos.workshop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.test.web.servlet.ResultActions;


public class ParseUtil {


    private ParseUtil(){}

    public static String getExceptionMessage(ResultActions resultActions) throws Exception{
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(responseContent);
        return jsonObject.getString("error");
    }

    public static  <T> String parseObjectToString(T object) throws  Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
