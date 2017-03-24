package com.romankaranchuk;

import java.util.Iterator;
import java.util.Map;


/**
 * Created by roman on 24.3.17.
 */
public class Shop {
    private Map<SportEquipment, Integer> goods;

    public Map<SportEquipment, Integer> getGoods() {
        return goods;
    }

    public void setGoods(Map<SportEquipment, Integer> goods) {
        this.goods = goods;
    }

    @Override
    public String toString(){
        Iterator it = goods.entrySet().iterator();
        Object[] colsNames = {"title", "price", "count"};
        String goodsString = String.format("%-20s%-20s%-10s",colsNames);
        goodsString = goodsString.concat("\n");
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            goodsString = goodsString.concat("\n"+pair.getKey().toString().concat(pair.getValue().toString()));
        }
        return goodsString;
    }
}
