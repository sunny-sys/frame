package io.smallbird.modules.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.smallbird.common.model.DetailResp;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Result;
import io.smallbird.common.validator.ValidatorUtils;
import io.smallbird.modules.sys.entity.SysFileEntity;
import io.smallbird.modules.sys.service.SysFileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xml
 * @email xxx
 * @date 2019-04-19 16:45:03
 */
@RestController
@RequestMapping("logic/sysfile")
@Api(value = "文件-接口", tags = "文件管理")
public class SysFileController extends AbstractController {
    @Value("${upload.rootPath}")
    private String uploadRootPath;

    @Value("${upload.relativePath}")
    private String uploadImgPath;
    @Autowired
    private SysFileService sysFileService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "返回上传成功的文件对象的id号集合")
    @ApiImplicitParam(name = "files", value = "文件对象数组", required = true, dataType = "File")
    public DetailResp upload(HttpServletRequest request) {
        logger.info("进入上传图片接口");
        try {
            List<SysFileEntity> sysFileEntities = uploadFile(request,uploadRootPath ,uploadImgPath);
            List<Long> list = sysFileEntities.stream().map(SysFileEntity::getFileId).collect(Collectors.toList());
            return DetailResp.success(list);
        } catch (Exception e) {
            return DetailResp.error("文件上传失败，请稍后重试");
        }
    }

    @RequestMapping(value = "/downloadImg", method = RequestMethod.GET)
    @ApiOperation(value = "展示图片", notes = "传入图片文件的id号")
    public DetailResp downloadImg(@ApiParam("图片文件id") String fileId, HttpServletResponse response) {
        try {
            downloadImage(fileId, response,uploadRootPath);
            return null;
        } catch (Exception e) {
            return DetailResp.error("文件下载失败，请稍后重试");
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "下载文件/图片", notes = "传入图片或文件的id号")
    public DetailResp downloadFile(@ApiParam("图片或文件id") String fileId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        download(fileId, response, request,uploadRootPath);
        return null;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("logic:sysfile:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysFileService.queryPage(params);

        return Result.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fileId}")
    @RequiresPermissions("logic:sysfile:info")
    public Result info(@PathVariable("fileId") Long fileId) {
        SysFileEntity sysFile = sysFileService.getById(fileId);

        return Result.ok().put("sysFile", sysFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("logic:sysfile:save")
    public Result save(@RequestBody SysFileEntity sysFile) {
        sysFileService.save(sysFile);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("logic:sysfile:update")
    public Result update(@RequestBody SysFileEntity sysFile) {
        ValidatorUtils.validateEntity(sysFile);
        sysFileService.updateById(sysFile);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("logic:sysfile:delete")
    public Result delete(@RequestBody Long[] fileIds) {
        sysFileService.removeByIds(Arrays.asList(fileIds));

        return Result.ok();
    }

}
