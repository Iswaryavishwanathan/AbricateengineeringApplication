package com.example.abricateengineering.Service;

import com.example.abricateengineering.DAO.DataRecordDAO;
import com.example.abricateengineering.entity.DataRecord;
import org.springframework.stereotype.Service;


@Service
public class DataDAOService {
     public DataRecordDAO convertToDAO(DataRecord dataRecord) {
        DataRecordDAO dataRecordDAO = new DataRecordDAO();
        dataRecordDAO.setFormula(dataRecord.getFormula());
        Integer[] tValues = {
            dataRecord.getT01(), dataRecord.getT02(), dataRecord.getT03(), dataRecord.getT04(),
            dataRecord.getT05(), dataRecord.getT06(), dataRecord.getT07(), dataRecord.getT08(),
            dataRecord.getT09(), dataRecord.getT10(), dataRecord.getT11(), dataRecord.getT12(),
            dataRecord.getT13(), dataRecord.getT14(), dataRecord.getT15(), dataRecord.getT16(),
            dataRecord.getT17(), dataRecord.getT18(), dataRecord.getT19(), dataRecord.getT20(),
            dataRecord.getT21(), dataRecord.getT22(), dataRecord.getT23(), dataRecord.getT24()
        };
        dataRecordDAO.setTValues(tValues);

        Integer[] aValues = {
            dataRecord.getA01(), dataRecord.getA02(), dataRecord.getA03(), dataRecord.getA04(),
            dataRecord.getA05(), dataRecord.getA06(), dataRecord.getA07(), dataRecord.getA08(),
            dataRecord.getA09(), dataRecord.getA10(), dataRecord.getA11(), dataRecord.getA12(),
            dataRecord.getA13(), dataRecord.getA14(), dataRecord.getA15(), dataRecord.getA16(),
            dataRecord.getA17(), dataRecord.getA18(), dataRecord.getA19(), dataRecord.getA20(),
            dataRecord.getA21(), dataRecord.getA22(), dataRecord.getA23(), dataRecord.getA24()
        };
        dataRecordDAO.setAValues(aValues);

        String[] nValues = {
            dataRecord.getN01(), dataRecord.getN02(), dataRecord.getN03(), dataRecord.getN04(),
            dataRecord.getN05(), dataRecord.getN06(), dataRecord.getN07(), dataRecord.getN08(),
            dataRecord.getN09(), dataRecord.getN10(), dataRecord.getN11(), dataRecord.getN12(),
            dataRecord.getN13(), dataRecord.getN14(), dataRecord.getN15(), dataRecord.getN16(),
            dataRecord.getN17(), dataRecord.getN18(), dataRecord.getN19(), dataRecord.getN20(),
            dataRecord.getN21(), dataRecord.getN22(), dataRecord.getN23(), dataRecord.getN24()
        };
        dataRecordDAO.setNValues(nValues);

        return dataRecordDAO;
    }

}
