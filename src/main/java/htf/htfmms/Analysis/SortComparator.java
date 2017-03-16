package htf.htfmms.Analysis;

import java.util.Comparator;

import htf.htfmms.Database.Mission;

/**
 * Created by Administrator on 2016/5/23.
 */
public class SortComparator implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        Mission m1 = (Mission) lhs;
        Mission m2 = (Mission) rhs;
        if (m1.getProcess().equals("100") && !m2.getProcess().equals("100")) return 0;
        if (!m1.getProcess().equals("100") && m2.getProcess().equals("100")) return 1;
        if (m1.getEndTime().compareTo(m2.getEndTime())>0) return 0;
        else return 1;
    }
}