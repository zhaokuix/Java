package utils;

import com.yonyougov.fbp.gaia.bean.CcidsDTO;
import com.yonyougov.fbp.gaia.bean.CompareMessage;

import java.util.ArrayList;
import java.util.List;

public class ClassCompareUtil {
    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值无差异
     *
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(CcidsDTO oldObject, CcidsDTO newObject, List<String> fileds) {
        List<CompareMessage> compareMessageList = compareFields(oldObject, newObject, fileds);

        if (compareMessageList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     *
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果map
     */
    @SuppressWarnings("rawtypes")
    public static List<CompareMessage> compareFields(CcidsDTO oldObject, CcidsDTO newObject, List<String> fileds) {

        List<CompareMessage> compareMessageList = null;
        Long coa = oldObject.getCoaId();

        try {
            compareMessageList = new ArrayList<>();
            for (String filed : fileds) {


                String oldValue = String.valueOf(PropertiesUtil.getProperty(oldObject, filed));
                String newValue = String.valueOf(PropertiesUtil.getProperty(newObject, filed));
                if (oldValue == null && newValue != null) {
                    compareMessageList.add(putMessage(oldObject, newObject, filed, oldValue, newValue));
                    continue;
                }

                if (oldValue != null && newValue == null) {
                    compareMessageList.add(putMessage(oldObject, newObject, filed, oldValue, newValue));
                    continue;
                }

                if (!oldValue.equals(newValue)) {
                    compareMessageList.add(putMessage(oldObject, newObject, filed, oldValue, newValue));
                    continue;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return compareMessageList;
    }

    static CompareMessage putMessage(CcidsDTO oldObject, CcidsDTO newObject, String name, Object oldValue, Object newValue) {
        CompareMessage compareMessage = new CompareMessage();
        compareMessage.setOldCcid(oldObject.getCcid());
        compareMessage.setNewCcid(oldObject.getCcid());
        compareMessage.setFiledName(name);
        compareMessage.setOldValue(String.valueOf(oldValue));
        compareMessage.setNewValue(String.valueOf(newValue));
        return compareMessage;
    }

}
