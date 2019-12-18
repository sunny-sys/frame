package io.smallbird.common.model;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.smallbird.common.utils.SpringContextUtils;
import io.smallbird.modules.sys.entity.SysDictEntity;
import io.smallbird.modules.sys.service.SysDictService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Data
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Lazy
class CommonDict {
    //字典值字段
    private Field valueField;
    //字典项名称字段
    private Field nameField;
    private List<SysDictEntity> dicItems;
    protected io.smallbird.common.annotation.Dictionary dictAnno;

    public void init(Class<?> clz, Field field) {
        String dbDictionaryType = dictAnno.value();
        String valueFieldName = dictAnno.valueField();
        if (valueFieldName == null || valueFieldName.trim().isEmpty()) {
            valueFieldName = dictAnno.dynamic() ? field.getName() : field.getName().replace("Desc", "");
        }
        valueField = getField(clz, valueFieldName);
        assertNotEmpty(valueField, "CommonDict valueField must not be null!");
        // 字典的ItemValue字段必须和ItemName字段分开来 用Desc
        nameField = getField(clz, valueFieldName + "Desc");
        SysDictService sysDictService = SpringContextUtils.getBean(SysDictService.class);
        dicItems = sysDictService.list(new LambdaQueryWrapper<SysDictEntity>().eq(SysDictEntity::getType, dbDictionaryType));
        assertNotEmpty(dicItems, "CommonDict dictValues must not be null!");
    }

    public void invertTranslate(Object target, Field field) {
        if (nameField == null) {
            return;
        }
        String itemName = null;
        //说明注解标注的是自动 值从
        itemName = (String) getFieldValue(target, nameField);

        if (itemName == null) {
            Map<String, String> extraMap = ((BaseEntity) target).getExtraMap();
            itemName = extraMap == null ? null : extraMap.get(nameField.getName());
        }
        if (itemName == null) {
            return;
        }
        String itemValue = getItemValue(itemName);
        setFieldValue(target, field, itemValue);
    }

    private String getItemValue(String itemValue) {
        String code = null;
        for (SysDictEntity item : dicItems) {
            if (item.getValue().equalsIgnoreCase(itemValue)) {
                code = item.getCode();
                break;
            }
        }
        return code;
    }

    public void translate(Object target, Field field) {
        Object dictValue = getFieldValue(target, valueField);
        if (dictValue == null) {
            return;
        }
        Object dictDisp = null;
        // TODO need rewrite, list is the performance bottleneck
        for (SysDictEntity item : dicItems) {
            if (item.getCode().equalsIgnoreCase(dictValue.toString())) {
                dictDisp = item.getValue();
                break;
            }
        }
        if (dictDisp == null) {
            String defaultValue = dictAnno.defaultValue();
            if (defaultValue == null) {
                return;
            }
            dictDisp = defaultValue;
        }
        if (getDictAnno().dynamic() && target instanceof BaseEntity) {
            ((BaseEntity) target).putExtraProperty(valueField.getName() + "Desc", dictDisp);
        } else {
            setFieldValue(target, field, dictDisp);
        }
    }

    protected Field getField(Class<?> clz, String fieldName) {
        Field field;
        try {
            field = clz.getDeclaredField(fieldName);

            if (field == null) return null;

            field.setAccessible(true);
        } catch (Exception e) {
            return null;
        }
        return field;
    }

    protected Object getFieldValue(Object target, Field field) {
        try {
            return field.get(target);
        } catch (Exception e1) {
            return null;
        }
    }

    protected void setFieldValue(Object target, Field field, Object value) {
        try {
            if (value.getClass() == String.class) {
                if (field.getType() == int.class || field.getType() == Integer.class) {
                    value = Integer.parseInt((String) value);
                } else if (field.getType() == short.class || field.getType() == Short.class) {
                    value = Short.parseShort((String) value);
                }
            }
            field.set(target, value);
        } catch (Exception e) {
        }
    }

    protected void assertNotEmpty(Object o) {
        assertNotEmpty(o, null);
    }

    protected void assertNotEmpty(Object o, String msg) {
        if (o == null) {
            throwException(msg);
        }
        if (o instanceof Collection<?>) {
            if (((Collection<?>) o).isEmpty()) {
                throwException(msg);
            }
        }
    }

    private void throwException(String msg) {
        if (msg == null) {
            throw new RuntimeException();
        }
        throw new RuntimeException(msg);
    }
}
