package io.smallbird.common.model;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.smallbird.common.utils.SpringContextUtils;
import io.smallbird.common.utils.Util;
import io.smallbird.modules.sys.entity.SysDictEntity;
import io.smallbird.modules.sys.service.SysDictService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 字典工具使用范例：
 * <p>
 * 1) 第一种方式：
 *
 * <pre>
 * @Dictionary(value = "VD_VEH_USE_TYPE", dynamic = true)
 * private Integer vehUseType;
 * </pre>
 *
 * <p>
 * "value = VD_VEH_USE_TYPE" 表示数据库 dictionary_type 表中 DIC_TYPE 字段的值，也可以使用 dictionary_type.properties 中的key;
 * "dynamic = true" 表示自动创建一个 vehUseTypeDesc 的属性并赋值，如果要使用 dynamic 则 bean 必须实现 JacksonAnyGetterSupport 接口。
 *
 * <p>
 * 2）第二种方式
 *
 * <pre>
 * private Short handleType;
 * @Dictionary("handle_type")
 * private String handleTypeDesc;
 * </pre>
 *
 * <p>
 * 这种方式比第一种方式少了一个 dynamic = true 的参数，并且是直接标注到 handleTypeDesc 显示值上的，翻译的时候会默认去掉该属性名的 Desc 来查找原始值的属性，
 * 再根据原始属性的值匹配字典项，您也可以通过valueField参数来指定原始值的属性名称，如：
 *
 * <pre>
 * private Short abc;
 * @Dictionary(value = "handle_type", valueField = "abc")
 * private String handleTypeDesc;
 * </pre>
 *
 * <p>
 * 最后需要显示的调用 DictionaryUtil.translate(T t) 或者 DictionaryUtil.translate(List t) 进行字典翻译。
 *
 * @Author: xupj
 * @Date: 2018/11/14 18:42
 */
@Component
public class DictionaryService {

    public void invertTranslate(Object t) {
        if (t == null) {
            return;
        }
        invertTranslate(Arrays.asList(t));
    }

    public void invertTranslate(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        _translate(list, true);
    }

    public void translate(Object t) {

        if (Util.isEmpty(t)) {
            return;
        }

        if (t instanceof List<?>) {
            _translate((List<?>) t, false);
        } else {
            _translate(Arrays.asList(t), false);
        }
    }

    private void _translate(List<?> list, boolean invert) {
        Map<Field, CommonDict> cache = buildCache(list);
        if (cache == null) {
            return;
        }

        Set<Map.Entry<Field, CommonDict>> needTranslateFields = cache.entrySet();

        if (needTranslateFields == null || needTranslateFields.isEmpty()) {
            return;
        }

        for (Object target : list) {
            for (Map.Entry<Field, CommonDict> e : needTranslateFields) {
                Field f = e.getKey();
                CommonDict c = e.getValue();

                try {
                    if (invert) {
                        c.invertTranslate(target, f);
                    } else {
                        c.translate(target, f);
                    }
                } catch (Exception e1) {
                }
            }
        }
    }

    private Map<Field, CommonDict> buildCache(List<?> list) {
        Object first = null;
        for (int i = 0; i < list.size(); i++) {
            first = list.get(i);
            if (first != null) {
                break;
            }
        }
        if (first == null) {
            return null;
        }
        Map<Field, CommonDict> cache = new HashMap<Field, CommonDict>();
        Class<?> clz = first.getClass();
        while (clz != Object.class) {
            buildCache(cache, clz);
            clz = clz.getSuperclass();
        }

        return cache;
    }

    private void buildCache(Map<Field, CommonDict> cache, Class<?> clz) {
        Field[] fields = clz.getDeclaredFields();
        if (fields.length == 0) {
            return;
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(io.smallbird.common.annotation.Dictionary.class)) {
                io.smallbird.common.annotation.Dictionary dictAnno = field.getAnnotation(io.smallbird.common.annotation.Dictionary.class);
                CommonDict commonDict = SpringContextUtils.getBean(CommonDict.class);
                commonDict.setDictAnno(dictAnno);
                commonDict.init(clz, field);
                if (commonDict == null) {
                    continue;
                }
                cache.put(field, commonDict);
            }
        }
    }
}

