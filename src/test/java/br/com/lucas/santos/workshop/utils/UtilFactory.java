package br.com.lucas.santos.workshop.utils;

import org.json.JSONObject;
import org.springframework.test.web.servlet.ResultActions;

public class UtilFactory {

    private UtilFactory(){}

    public static  String getExceptionMessage(ResultActions resultActions) throws Exception{
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(responseContent);
        return jsonObject.getString("error");
    }
}
