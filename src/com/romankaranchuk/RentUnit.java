package com.romankaranchuk;

import java.util.ArrayList;

/**
 * Created by roman on 24.3.17.
 */
public class RentUnit {
    public ArrayList<SportEquipment> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<SportEquipment> units) {
        this.units = units;
    }

    private ArrayList<SportEquipment> units;

    @Override
    public String toString(){
        Object[] colsNames = {"rent units name:","price:"};
        String outputLine = String.format("%-20s%-20s", colsNames);
        for (SportEquipment sportEquipment : units){
            outputLine = outputLine.concat("\n"+sportEquipment.toString());
        }
        return outputLine;
    }

}
