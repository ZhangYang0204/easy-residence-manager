package pers.zhangyang.easyresidencemanager.service;

import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyresidencemanager.exception.*;
import pers.zhangyang.easyresidencemanager.meta.ResidenceBlockMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceInventoryContentMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;

import java.util.List;

public interface GuiService {
     List<ResidenceMeta> listResidence();
    void createResidence(ResidenceMeta residenceMeta) throws DuplicateResidenceException, ConflictResidenceException;
    void deleteResidence(String name) throws NotExistResidenceException, NotSetUpResidenceException;
    void setResidenceOwner(String ownerUuid,ResidenceMeta residenceMeta0) throws NotExistResidenceException, StateChangeException;
    @Nullable
    ResidenceMeta getResidenceByUuid(String uuid) ;

    void setResidenceName(String residenceUuid,String name) throws NotExistResidenceException, DuplicateResidenceException;

    List<ResidenceMeta> listResidence(String ownerUuid);
    List[] setupResidenceAndGetTotal(String uuid) throws NotExistResidenceException, DuplicateSetupResidenceException;
    void cancelSetupResidence(String uuid,List<ResidenceBlockMeta> residenceBlockMetaList,
                        List<ResidenceInventoryContentMeta> residenceInventoryContentMetaList) throws NotExistResidenceException, NotSetUpResidenceException;
}
