package pers.zhangyang.easyresidencemanager.service.impl;

import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.util.LocationUtil;
import pers.zhangyang.easyresidencemanager.dao.ResidenceBlockDao;
import pers.zhangyang.easyresidencemanager.dao.ResidenceDao;
import pers.zhangyang.easyresidencemanager.dao.ResidenceInventoryContentDao;
import pers.zhangyang.easyresidencemanager.exception.*;
import pers.zhangyang.easyresidencemanager.meta.ResidenceBlockMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceInventoryContentMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;

import java.util.Collections;
import java.util.List;

public class GuiServiceImpl implements GuiService {
    @Override
    public List<ResidenceMeta> listResidence() {
        List<ResidenceMeta> residenceMetaList=new ResidenceDao().list();
        Collections.reverse(residenceMetaList);
        return  residenceMetaList;
    }

    @Override
    public void createResidence(ResidenceMeta residenceMeta) throws DuplicateResidenceException, ConflictResidenceException {
        if (new ResidenceDao().getByUuid(residenceMeta.getUuid())!=null){
            throw new DuplicateResidenceException();
        }

        List<ResidenceMeta> residenceMetaList=new ResidenceDao().list();
        for (ResidenceMeta r:residenceMetaList){
            if (LocationUtil.blockIsIn(LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()),
                    LocationUtil.deserializeLocation(residenceMeta.getSecondLocation()),
                    LocationUtil.deserializeLocation(r.getFirstLocation()))){
                throw new ConflictResidenceException();
            }
            if (LocationUtil.blockIsIn(LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()),
                    LocationUtil.deserializeLocation(residenceMeta.getSecondLocation()),
                    LocationUtil.deserializeLocation(r.getSecondLocation()))){
                throw new ConflictResidenceException();
            }


            if (LocationUtil.blockIsIn(LocationUtil.deserializeLocation(r.getFirstLocation()),
                    LocationUtil.deserializeLocation(r.getSecondLocation()),
                    LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()))){
                throw new ConflictResidenceException();
            }
            if (LocationUtil.blockIsIn(LocationUtil.deserializeLocation(r.getFirstLocation()),
                    LocationUtil.deserializeLocation(r.getSecondLocation()),
                    LocationUtil.deserializeLocation(residenceMeta.getSecondLocation()))){
                throw new ConflictResidenceException();
            }
        }
        new ResidenceDao().insert(residenceMeta);
    }


    @Override
    public void deleteResidence(String name) throws NotExistResidenceException, NotSetUpResidenceException {
        ResidenceMeta residenceMeta=new ResidenceDao().getByName(name);
        if (residenceMeta==null){
            throw new NotExistResidenceException();
        }
        if (!residenceMeta.isSetup()){
            throw new NotSetUpResidenceException();
        }
        new ResidenceBlockDao().deleteByResidenceUuid(residenceMeta.getUuid());
        new ResidenceInventoryContentDao().deleteByResidenceUuid(residenceMeta.getUuid());

        new ResidenceDao().deleteByUuid(residenceMeta.getUuid());
    }

    @Override
    public void setResidenceOwner(String ownerUuid,ResidenceMeta residenceMeta0) throws NotExistResidenceException, StateChangeException {
        ResidenceMeta residenceMeta=new ResidenceDao().getByUuid(residenceMeta0.getUuid());
        if (residenceMeta==null){
            throw new NotExistResidenceException();
        }
        if (!residenceMeta.equals(residenceMeta0)){
            throw new StateChangeException();
        }
        residenceMeta.setOwnerUuid(ownerUuid);
        new ResidenceDao().deleteByUuid(residenceMeta.getUuid());

        new ResidenceDao().insert(residenceMeta);


    }

    @Override
    public @Nullable ResidenceMeta getResidenceByUuid(String uuid)   {
        return  new ResidenceDao().getByUuid(uuid);
    }

    @Override
    public void setResidenceName(String residenceUuid, String name) throws NotExistResidenceException, DuplicateResidenceException {
        ResidenceMeta residenceMeta=new ResidenceDao().getByUuid(residenceUuid);
        if (residenceMeta==null){
            throw new NotExistResidenceException();
        }
        if (new ResidenceDao().getByName(name)!=null){
            throw new DuplicateResidenceException();
        }
        residenceMeta.setName(name);
        new ResidenceDao().deleteByUuid(residenceMeta.getUuid());
        new ResidenceDao().insert(residenceMeta);
    }

    @Override
    public List<ResidenceMeta> listResidence(String ownerUuid) {
        List<ResidenceMeta> residenceMetaList=new ResidenceDao().listByOwnerUuid(ownerUuid);



        Collections.reverse(residenceMetaList);
        return  residenceMetaList;
    }


    @Override
    public List[] setupResidenceAndGetTotal(String uuid) throws NotExistResidenceException, DuplicateSetupResidenceException {
        ResidenceMeta residenceMeta=new ResidenceDao().getByUuid(uuid);
        if (residenceMeta==null){
            throw new NotExistResidenceException();
        }
        if (residenceMeta.isSetup()){
            throw new DuplicateSetupResidenceException();
        }
        List[] lists=new List[2];
        lists[0]=new ResidenceBlockDao().listByResidenceUuid(residenceMeta.getUuid());
        lists[1]=new ResidenceInventoryContentDao().listByResidenceUuid(residenceMeta.getUuid());
        new ResidenceBlockDao().deleteByResidenceUuid(residenceMeta.getUuid());
        new ResidenceInventoryContentDao().deleteByResidenceUuid(residenceMeta.getUuid());

        residenceMeta.setSetup(true);
        new ResidenceDao().deleteByUuid(residenceMeta.getUuid());
        new ResidenceDao().insert(residenceMeta);

        return lists;

    }

    @Override
    public void cancelSetupResidence(String uuid, List<ResidenceBlockMeta> residenceBlockMetaList, List<ResidenceInventoryContentMeta> residenceInventoryContentMetaList) throws NotExistResidenceException, NotSetUpResidenceException {
        ResidenceMeta residenceMeta=new ResidenceDao().getByUuid(uuid);
        if (residenceMeta==null){
            throw new NotExistResidenceException();
        }
        if (!residenceMeta.isSetup()){
            throw new NotSetUpResidenceException();
        }
        for (ResidenceBlockMeta r:residenceBlockMetaList){
            new ResidenceBlockDao().insert(r);
        }
        for (ResidenceInventoryContentMeta r: residenceInventoryContentMetaList){
            new ResidenceInventoryContentDao().insert(r);
        }
        residenceMeta.setSetup(false);
        new ResidenceDao().deleteByUuid(residenceMeta.getUuid());
        new ResidenceDao().insert(residenceMeta);
    }
}
