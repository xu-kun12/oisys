package cn.njcit.controller;

import cn.njcit.entity.OIClass;
import cn.njcit.service.IClassService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 班级信息 前端控制器
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private IClassService classService;

    @GetMapping("/searchClass")
    public ResponseResult searchClass(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(required = false) String className){
        PageInfo<OIClass> oiClassPageInfo = classService.getOIClassList(page,className);
        return ResponseResult.ok().put("classList",oiClassPageInfo);
    }

    @PostMapping("save")
    public ResponseResult saveOIclasses(OIClass oiClass){
        if(oiClass.getClassId() == null){
            QueryWrapper<OIClass> wrapper = new QueryWrapper<>();
            wrapper.eq("class_no",oiClass.getClassNo());
            if(classService.count(wrapper)>0){
                return ResponseResult.error("班级编号不能重复");
            }
        }
       return classService.saveOrUpdate(oiClass) ?ResponseResult.ok("保存成功！") : ResponseResult.error("保存失败");
    }

    @GetMapping("/edit")
    public ResponseResult classEdit(@RequestParam(required = false) Integer classId){
        ResponseResult responseResult = ResponseResult.ok();
        if(classId!=null){
            OIClass oiClass = classService.searchClassById(classId);
            responseResult.put("classById",oiClass);
        }
        return responseResult;
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteClass(Long classId){
        OIClass oiClass = new OIClass();
        oiClass.setClassId(classId);
        return classService.removeById(oiClass) ? ResponseResult.ok("成功删除") : ResponseResult.error("删除失败");
    }
}
