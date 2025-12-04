/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.dtos.ParamListDTO;
import com.base.frame.socle.core.entity.ParamList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Bouchara
 */
public interface ParamListDTORepository extends JpaRepository<ParamList, String> {

    @Query("select new com.base.frame.carnet.sante.dtos.ParamListDTO(tp.id,tp.code,tp.libelle) from ParamList tp where tp.paramCode.code=:paramCode")
    public List<ParamListDTO>getParamListDTO(@Param("paramCode") String paramCode);

}
