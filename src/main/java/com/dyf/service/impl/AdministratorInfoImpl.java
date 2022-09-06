package com.dyf.service.impl;

import com.dyf.dao.IAdministratorInfoDAO;
import com.dyf.entity.AdministratorInfo;
import com.dyf.enums.ResultEnum;
import com.dyf.exception.SellException;
import com.dyf.service.IAdministratorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorInfoImpl implements IAdministratorInfoService {
    @Autowired
    private IAdministratorInfoDAO iAdministratorInfoDAO;

    @Override
    public AdministratorInfo findByAdminId(String adminId) {
        AdministratorInfo administratorInfo = iAdministratorInfoDAO.findByAdminId(adminId);
        if (administratorInfo == null) {
            throw new SellException(ResultEnum.ADMIN_NOT_EXIST);
        }
        return administratorInfo;
    }
}
