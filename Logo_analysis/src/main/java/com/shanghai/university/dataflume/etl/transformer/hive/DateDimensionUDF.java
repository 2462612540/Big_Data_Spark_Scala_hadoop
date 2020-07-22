package com.shanghai.university.dataflume.etl.transformer.hive;

import com.shanghai.university.dataflume.etl.common.DateEnum;
import com.shanghai.university.dataflume.etl.transformer.model.dim.base.DateDimension;
import com.shanghai.university.dataflume.etl.transformer.service.IDimensionConverter;
import com.shanghai.university.dataflume.etl.transformer.service.impl.DimensionConverterImpl;
import com.shanghai.university.dataflume.etl.util.TimeUtil;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;



/**
 * 操作日期dimension 相关的udf
 * 
 * @author root
 *
 */
public class DateDimensionUDF extends UDF {
    private IDimensionConverter converter = new DimensionConverterImpl();

   

    /**
     * 根据给定的日期（格式为:yyyy-MM-dd）至返回id
     * 
     * @param day
     * @return
     */
    public IntWritable evaluate(Text day) {
        DateDimension dimension = DateDimension.buildDate(TimeUtil.parseString2Long(day.toString()), DateEnum.DAY);
        try {
            int id = this.converter.getDimensionIdByValue(dimension);
            return new IntWritable(id);
        } catch (IOException e) {
            throw new RuntimeException("获取id异常");
        }
    }
}
