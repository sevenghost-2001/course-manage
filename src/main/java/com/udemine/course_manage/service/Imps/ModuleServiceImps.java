package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.ModuleCreationRequest;
import com.udemine.course_manage.entity.Course;
import com.udemine.course_manage.entity.Module;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.mapper.ModuleMapper;
import com.udemine.course_manage.repository.CourseRepository;
import com.udemine.course_manage.repository.ModuleRepository;
import com.udemine.course_manage.service.Services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImps implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    public List<Module> getAllModules(){

        return moduleRepository.findAll();
    }

    @Override
    public Module createModule(ModuleCreationRequest request){
        Module module = new Module();
        if(moduleRepository.existsByTitle(request.getTitle())){
                throw new AppException(ErrorCode.TITLE_EXISTED);
            }
        if(moduleRepository.existsByPosition(request.getPosition())){
            throw new AppException(ErrorCode.POSITION_MODULE_EXISTED);
        }
        module = moduleMapper.toModule(request);
        return moduleRepository.save(module);
    }

    @Override
    public Module updateModule(int id, ModuleCreationRequest request){
        //Bước 1 : check ID có tồn tại hay không
        //Bước 2 : nếu ko tồn tại thì thẩy error tồn tại thì update
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if(optionalModule.isEmpty()){
            throw new AppException(ErrorCode.MODULE_NOT_EXIST);
        }
        Module module = optionalModule.get();
        moduleMapper.updateModule(module,request);
        return moduleRepository.save(module);
    }

    @Override
   public void deleteModule(int id){
        if(!moduleRepository.existsById(id)){
            throw new AppException(ErrorCode.MODULE_NOT_EXIST);
        }
        moduleRepository.deleteById(id);
   }
}
