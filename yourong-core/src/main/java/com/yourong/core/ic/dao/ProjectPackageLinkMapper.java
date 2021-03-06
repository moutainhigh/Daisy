package com.yourong.core.ic.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yourong.core.ic.model.Project;
import com.yourong.core.ic.model.ProjectPackageBiz;
import com.yourong.core.ic.model.ProjectPackageLink;

public interface ProjectPackageLinkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ic_project_package_link
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ic_project_package_link
     *
     * @mbggenerated
     */
    int insert(ProjectPackageLink record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ic_project_package_link
     *
     * @mbggenerated
     */
    int insertSelective(ProjectPackageLink record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ic_project_package_link
     *
     * @mbggenerated
     */
    ProjectPackageLink selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ic_project_package_link
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProjectPackageLink record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ic_project_package_link
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ProjectPackageLink record);
    
    /**
     * 获取项目包内所有项目列表
     * @return
     */
    List<ProjectPackageBiz> selectProjectPackageBiz(@Param("map")Map<String, Object> map);
    
    /**
     * 获取项目包内所有项目列表数量
     * @return
     */
    int selectProjectPackageBizCount(@Param("map")Map<String, Object> map);
    /**
     * 判断是否添加过项目
     * @param originalProjectNumber
     * @return
     */
    int  findProjectByPackage(Long projectId);
    /**
     * 删除资产包的xiangm
     * @param record
     * @return
     */
    
    int batchDelete(@Param("ids") long[] id_array);
    /**
     * 获取所有资产包内的项目数据
     * @param projectPackageId
     * @return
     */
    List<ProjectPackageBiz> selectAllProjectPackageBiz(long projectPackageId);
    /**
     * 删除资产包下的所有项目信息
     * @param projectPackageId
     * @return
     */
    int deleteProjectFromPackage(@Param("projectPackageId")Long projectPackageId);
    
    /**
     * 获取资产包里的所有项目列表
     * @param projectPackageId
     * @return
     */
    List<ProjectPackageLink> selectByPackageList(@Param("projectPackageId") Long projectPackageId);
    /**
     *  根据项目Id获取项目包列表
     * @param projectId
     * @return
     */
    List<ProjectPackageLink> getProjectPackageByProjectId(@Param("projectId") Long projectId);
    /**
     * 判断该项目是否被其它工程引用
     * @param projectId
     * @return
     */
    int countPackageLinkProject(@Param("projectId")Long projectId,@Param("projectPackageId") Long projectPackageId);

}