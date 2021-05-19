package howard.cinema.core.dao.dict.acl;

import com.google.common.collect.Lists;
import hx.base.core.dao.dict.salary.PrizeType;

import java.util.List;

/**
 *@ClassName PositionsClass.java
 *@Description 职级
 *@Author HuoJiaJin
 *@Date 2020/6/18 16:09
 *@Version 1.0
 **/
public enum PositionsClass {
    TC("客户经理", 1){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    PBC("业务主任", 2){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    BC("组经理", 3){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.GROUPBUSINESS, PrizeType.GROUPMANPOWER, PrizeType.EXCELLENTGROUP, PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    SBC("高级组经理", 4){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.GROUPBUSINESS, PrizeType.GROUPMANPOWER, PrizeType.EXCELLENTGROUP, PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    BM("区域总经理", 5){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.SECTIONBUSINESS, PrizeType.GROUPBUSINESS, PrizeType.SECTIONMANPOWER, PrizeType.GROUPMANPOWER, PrizeType.EXCELLENTSECTION, PrizeType.EXCELLENTGROUP, PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    SBM("资深总经理", 6){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.SECTIONBUSINESS, PrizeType.GROUPBUSINESS, PrizeType.SECTIONMANPOWER, PrizeType.GROUPMANPOWER, PrizeType.EXCELLENTSECTION, PrizeType.EXCELLENTGROUP, PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    AS("总监", 7){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.CHIEFBUSINESS, PrizeType.SECTIONBUSINESS, PrizeType.GROUPBUSINESS, PrizeType.CHIEFMANPOWER, PrizeType.SECTIONMANPOWER, PrizeType.GROUPMANPOWER, PrizeType.EXCELLENTSECTION, PrizeType.EXCELLENTGROUP, PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    SAS("执行总监", 8){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.CHIEFBUSINESS, PrizeType.SECTIONBUSINESS, PrizeType.GROUPBUSINESS, PrizeType.CHIEFMANPOWER, PrizeType.SECTIONMANPOWER, PrizeType.GROUPMANPOWER, PrizeType.EXCELLENTSECTION, PrizeType.EXCELLENTGROUP, PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    GAS("首席总监", 9){
        @Override
        public List<PrizeType> getPrizeTypes() {
            return Lists.newArrayList(PrizeType.CHIEFBUSINESS, PrizeType.SECTIONBUSINESS, PrizeType.GROUPBUSINESS, PrizeType.CHIEFMANPOWER, PrizeType.SECTIONMANPOWER, PrizeType.GROUPMANPOWER, PrizeType.EXCELLENTSECTION, PrizeType.EXCELLENTGROUP, PrizeType.INDIVIDUALHALFYEAR);
        }
    },
    ;

    PositionsClass(String value, int code) {
        this.value = value;
        this.code = code;
    }

    private String value;
    private int code;

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }

    public abstract List<PrizeType> getPrizeTypes();

    /**
     * @Name isNotDirector
     * @Author HuoJiaJin
     * @Description 判断是否非主管职级
     * @Date 2021/2/25 0:20
     * @Param [positionsClass]
     * @Return boolean
     **/
    public static boolean isNotExecutive(PositionsClass positionsClass){
        return positionsClass == PositionsClass.TC || positionsClass == PositionsClass.PBC;
    }
}
