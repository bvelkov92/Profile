package com.profile.service.serviceAnotation;


import com.profile.models.entity.Project;

import java.util.List;


public interface ProjectService {

    List<Project> getMyAllProjects (Long id);
}
