package com.three.sharecare.bootapi.api.support;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springside.modules.web.MediaTypes;

import java.io.IOException;
import java.lang.reflect.Type;


/**
 * 二次封装统一为ResponseResult
 */
public class CustomResponseHandler extends MappingJackson2HttpMessageConverter {


    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        MediaType contentType = outputMessage.getHeaders().getContentType();
        JsonEncoding encoding = getJsonEncoding(contentType);
        JsonGenerator generator = this.objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
        try {
            writePrefix(generator, object);
            if(object instanceof ResponseResult){
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
                ResponseEntity<ResponseResult> responseEntity =
                        new ResponseEntity<>((ResponseResult)object, headers, HttpStatus.OK);
                this.objectMapper.writeValue(generator, responseEntity);
            }else{
                this.objectMapper.writeValue(generator, object);
            }
            writeSuffix(generator, object);
            generator.flush();
        }
        catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write content: " + ex.getMessage(), ex);
        }
    }
}
