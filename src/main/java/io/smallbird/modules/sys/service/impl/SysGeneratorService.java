package io.smallbird.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.smallbird.common.utils.GenUtils;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Query;
import io.smallbird.modules.sys.dao.GeneratorDao;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 *
 */
@Service
public class SysGeneratorService {

	@Autowired
	private GeneratorDao generatorDao;

	public PageUtils queryList(Map<String, Object> params) {

		Map<String, Object> newMap = new HashMap<>(params);
		newMap.put("limit", 0);
		List<Map<String, Object>> list = generatorDao.queryList(newMap);

		//分页参数
		int pageIndex = Integer.parseInt(params.get("page").toString());
		int limit = Integer.parseInt(params.get("limit").toString());
		params.put("offset", (pageIndex - 1) * limit);
		params.put("page", pageIndex);
		params.put("limit", limit);

		IPage<Map<String, Object>> page = new Query<Map<String, Object>>().getPage(params);
		page.setTotal(list.size());
		page.setRecords(generatorDao.queryList(params));

		return new PageUtils(page);
	}

	public Map<String, String> queryTable(String tableName) {
		return generatorDao.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return generatorDao.queryColumns(tableName);
	}

	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
