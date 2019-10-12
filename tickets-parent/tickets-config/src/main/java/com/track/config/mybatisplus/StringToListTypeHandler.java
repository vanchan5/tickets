package com.track.config.mybatisplus;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-01 20:39
 *
 * sql查出来的String转list
 *
 * 用法:
 *  <resultMap id="findByCondition" type="com.PmGoodsAttributeVo">
 *      <id column="id" property="id"/>
 *         <result column="name" property="name"/>
 *         <result column="sort" property="sort"/>
 *         <result column="valueList" property="valueList" javaType="java.util.List" jdbcType="VARCHAR" typeHandler="com.chauncy.web.config.mybatisplus.StringToListTypeHandler"/>
 *  </resultMap>
 *
 *  <select id="find" resultMap="findByCondition">
 *         select a.*,GROUP_CONCAT(CONCAT(b.value))as valueList
 *         from pm_goods_attribute a,pm_goods_attribute_value b
 *         <where>
 *             <if test="type !=null and type !=''">
 *                 type = #{type}
 *             </if>
 *             <if test="name !=null and name !=''">
 *                 and name like concat('%', #{name}, '%')
 *             </if>
 *             and a.id = b.product_attribute_id and a.del_flag = 0 and b.del_flag = 0
 *         </where>
 *  </select>
 *         group by a.id
 */
@MappedTypes({List.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class StringToListTypeHandler extends BaseTypeHandler<List<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String str = Joiner.on(",").skipNulls().join(parameter);
        ps.setString(i, str);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.stringToList(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.stringToList(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.stringToList(cs.getString(columnIndex));
    }

    private List<String> stringToList(String str) {
        return Strings.isNullOrEmpty(str) ? new ArrayList<>() : Splitter.on(",").splitToList(str);
    }
}
