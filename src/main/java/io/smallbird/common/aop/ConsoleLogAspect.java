package io.smallbird.common.aop;

import com.google.gson.Gson;
import io.smallbird.common.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Aspect
@Component
@Slf4j
@Order(3)
public class ConsoleLogAspect {

    @Pointcut(value = "(execution(* io.smallbird.modules.*.controller.*(..)))")
    public void requestLog() {}

    @Before("requestLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("------->->->->->- print request info ->->->->->-------");
        log.info(request.getMethod() + "  " + request.getRequestURL().toString());
        if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> paramMap = new HashMap<>();
            parameterMap.forEach((key, value) -> paramMap.put(key, String.join(",", value)));
            log.info(new Gson().toJson(paramMap));
        } else if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
            Object[] args = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            Arrays.stream(args).forEach(object -> {
                if (object instanceof BaseEntity){
                    sb.append("0");
                    log.info(new Gson().toJson(object));
                }
            });
            if (sb.length() == 0){
                log.info("{}");
            }
        }
    }

    @AfterReturning(pointcut = "requestLog()",returning = "result")
    public void doAfterReturning(Object result) {
        log.info("------->->->->->- print response info  ->->->->->-------");
        if (ObjectUtils.isEmpty(result)){
            log.info("{}");
            return;
        }
//        GsonBuilder builder = new GsonBuilder();
//
//        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
//            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                return new Date(json.getAsJsonPrimitive().getAsLong());
//            }
//        });
        log.info(new Gson().toJson(result));
    }
}
