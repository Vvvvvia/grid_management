package com.tang.entity.DTO;

import com.tang.entity.SellRecords;

import java.io.Serializable;
import java.util.List;

public class SellRecordsDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SellRecords> sellRecordsList;

    public List<SellRecords> getSellRecordsList() {
        return sellRecordsList;
    }

    public void setSellRecordsList(List<SellRecords> sellRecordsList) {

        this.sellRecordsList = sellRecordsList;
    }

    @Override
    public String toString() {
        return "SellRecordsDto{" +
                "sellRecordsList=" + sellRecordsList +
                '}';
    }
}
