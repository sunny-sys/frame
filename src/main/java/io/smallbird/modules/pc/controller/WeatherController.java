package io.smallbird.modules.pc.controller;

import io.smallbird.common.model.DetailResp;
import io.smallbird.modules.pc.vo.WeatherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Api("过去天气接口管理")
@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("获取天气信息")
    @GetMapping("getWeather/{cityName}")
    public DetailResp getWeather(@ApiParam(value = "城市名称,不要带市和区; 如: 青岛, 微山"
            , name = "cityName", required = true) @PathVariable("cityName") String cityName) {
        String url = "https://www.tianqiapi.com/api/?version=v6&city=" + cityName;
        WeatherVo weatherVo = restTemplate.getForObject(url, WeatherVo.class);
        return DetailResp.success(weatherVo);
    }
}
