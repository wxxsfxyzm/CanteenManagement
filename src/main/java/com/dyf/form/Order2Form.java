package com.dyf.form;

import com.dyf.dto.GoodsDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Order2Form implements Serializable {
    private static final long serialVersionUID = 1l;

    private List<GoodsDTO> goodsDTOList;
}
