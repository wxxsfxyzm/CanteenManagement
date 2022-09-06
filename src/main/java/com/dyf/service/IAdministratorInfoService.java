package com.dyf.service;

import com.dyf.entity.AdministratorInfo;

public interface IAdministratorInfoService {
    AdministratorInfo findByAdminId(String adminId);

}
