package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类
 * @author jiaqla
 *
 */
public class ListUtil {

	/**
	 * 分组list 每次取999条
	 * @param list
	 * @return
	 */
public static List<List> groupList(List list){    
        int listSize=list.size();
        int toIndex=999;
        List<List> groupList = new ArrayList<List>();  
        for(int i = 0;i<list.size();i+=toIndex){
        	if(i+toIndex>listSize){        
        		toIndex=listSize-i;
        	}
        	List newList = list.subList(i,i+toIndex);
        	groupList.add(newList);
        }   
        return groupList;
    }
}
