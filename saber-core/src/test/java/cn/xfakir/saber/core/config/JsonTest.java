package cn.xfakir.saber.core.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {
    public static void main(String[] args) throws JsonProcessingException {
        Saber saber = new Saber("Altria Pendragon","Excalibur");
        Object o = saber;
        ObjectMapper  objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(o));
    }
}
