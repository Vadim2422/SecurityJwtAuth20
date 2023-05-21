package com.flumine.securityjwtauth20.repositories;

import com.flumine.securityjwtauth20.models.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DevicesRepository extends JpaRepository<DeviceModel, Long> {
    List<DeviceModel> getAllByUserid(Long userid);
    DeviceModel getById(Long id);
}
