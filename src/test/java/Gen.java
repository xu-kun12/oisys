import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class Gen {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/oisys-ren?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
        FastAutoGenerator.create(url, "root", "123456").globalConfig(builder -> {
                    builder.author("njcit") // 设置作者
                            .outputDir("D://outttt"); // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent("cn.njcit") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://outptttt/mapper")); // 设置mapperXml生成路径
                }).strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder.addInclude("aoa_user", "aoa_user_login_record", "aoa_class", "aoa_course", "aoa_course_manage", "aoa_attend_class_setting", "aoa_attends_list")
                            .addTablePrefix("aoa_"); // 设置过滤表前缀
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板
                .execute();
    }

}
