package io.smallbird.modules.pc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.smallbird.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("天气响应实体")
@Data
@Accessors(chain = true)
public class WeatherVo extends BaseEntity {

    @ApiModelProperty("城市id")
    @JsonProperty("cityid")
    private String cityId;

    @ApiModelProperty("星期")
    private String week;

    @ApiModelProperty("气象台更新时间")
    @JsonProperty("update_time")
    private String updateTime;

    @ApiModelProperty("城市英文")
    private String cityEn;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("国家英文")
    private String countryEn;

    @ApiModelProperty("天气情况")
    private String wea;

    @ApiModelProperty("天气对应图标(xue, lei, shachen, wu, bingbao, yun, yu, yin, qing)")
    @JsonProperty("wea_img")
    private String weaImg;

    @ApiModelProperty("当前温度")
    private String tem;

    @ApiModelProperty("高温/白天温度")
    private String tem1;

    @ApiModelProperty("低温/晚上温度")
    private String tem2;

    @ApiModelProperty("风向")
    private String win;

    @ApiModelProperty("风速等级")
    @JsonProperty("win_speed")
    private String winSpeed;

    @ApiModelProperty("风速 如: 12km/h")
    @JsonProperty("win_meter")
    private String winMeter;

    @ApiModelProperty("湿度")
    private String humidity;

    @ApiModelProperty("能见度")
    private String visibility;

    @ApiModelProperty("气压hPa")
    private String pressure;

    @ApiModelProperty("空气质量")
    private String air;

    @ApiModelProperty("PM2.5")
    @JsonProperty("air_pm25")
    private String airPm25;

    @ApiModelProperty("空气质量等级")
    @JsonProperty("air_level")
    private String airLevel;

    @ApiModelProperty("空气质量描述")
    @JsonProperty("air_tips")
    private String airTips;

    @ApiModelProperty("预警信息")
    private AlarmBean alarm;

    @ApiModelProperty("当前日期")
    private String date;

    @Data
    public static class AlarmBean {
        @ApiModelProperty("预警类型")
        @JsonProperty("alarm_type")
        private String alarmType;

        @ApiModelProperty("预警级别")
        @JsonProperty("alarm_level")
        private String alarmLevel;

        @ApiModelProperty("预警信息")
        @JsonProperty("alarm_content")
        private String alarmContent;
    }
}
