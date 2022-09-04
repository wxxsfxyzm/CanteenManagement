package com.dyf.dao;

import com.dyf.entity.AdministratorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdministratorInfoDAO extends JpaRepository<AdministratorInfo, String>
{
    AdministratorInfo findByAdminId(String adminId);
}
