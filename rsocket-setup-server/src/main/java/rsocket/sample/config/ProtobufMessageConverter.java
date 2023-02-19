package rsocket.sample.config;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * Protobuf对象转换，自动完成protobuf对象序列化和反序列化，让controller的接口脱离这些重复工作
 * @author veione
 * @version 1.0
 * @date 2023/2/19
 */
public class ProtobufMessageConverter extends AbstractHttpMessageConverter<GeneratedMessage> {

    public ProtobufMessageConverter() {
        //设置该转换器支持的媒体类型
        super(new MediaType("application", "x-protobuf", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> arg0) {
        //如果是GeneratedMessage额度子类则支持
        return Message.class.isAssignableFrom(arg0);
    }

    @Override
    protected GeneratedMessage readInternal(Class<? extends GeneratedMessage> arg0, HttpInputMessage arg1) throws IOException, HttpMessageNotReadableException {
        Method parseMethod;
        try {
            //利用反射机制获得parseFrom方法
            parseMethod = arg0.getDeclaredMethod("parseFrom", InputStream.class);
            return (GeneratedMessage) parseMethod.invoke(arg0, arg1.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void writeInternal(GeneratedMessage arg0, HttpOutputMessage arg1) throws IOException, HttpMessageNotWritableException {
        //不用自己设置type，父类会根据构造函数中给的type设置
        OutputStream outputStream = arg1.getBody();

        //自己直接设置contentLength会导致异常,覆盖父类的getContentLength
        arg0.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    protected Long getContentLength(GeneratedMessage t, MediaType contentType) throws IOException {
        return Long.valueOf(t.toByteArray().length);
    }
}
